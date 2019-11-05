package com.github.earthofmarble.utility.mapper.service;

import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;
import com.github.earthofmarble.utility.mapper.exception.ClassConstructorException;
import com.github.earthofmarble.utility.mapper.exception.IsNotConvertibleException;
import com.github.earthofmarble.utility.mapper.exception.WrongReferencedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
public class Mapper {

    private static Logger logger = LoggerFactory.getLogger(Mapper.class);
    private Map<String, Function<String, Object>> functionsMap = new HashMap<>();

    public Mapper() {
        fillFuntionMap();
    }

    private void fillFuntionMap() {
        functionsMap.clear();
        functionsMap.put("Integer", Integer::valueOf);
        functionsMap.put("Short", Integer::valueOf);
        functionsMap.put("Long", Integer::valueOf);
        functionsMap.put("Byte", Byte::valueOf);
        functionsMap.put("Float", Float::valueOf);
        functionsMap.put("Double", Float::valueOf);
        functionsMap.put("Boolean", Float::valueOf);
        functionsMap.put("String", String::valueOf);
    }

    private Object setPrimitive(Object model, Class modelClazz) {
        return functionsMap.get(modelClazz.getSimpleName()).apply(String.valueOf(model));
    }

    private void connectFields(Field[] modelFields, Map<String, Field> convFieldMap, Object model, Object convertedModel) {
        for (Field modelField : modelFields) {
            modelField.setAccessible(true);
            ReferencedField modelFieldAnnotation = modelField.getAnnotation(ReferencedField.class);
            String referencedField = modelField.getName();
            Field convField = convFieldMap.get(referencedField);
            PropertyType propertyType = getPropertyType(modelFieldAnnotation);
            try {
                if (convField != null) {
                    convField.setAccessible(true);
                    if (modelField.get(model) != null) {

                        switch (propertyType) {
                            case SIMPLE:
                                simpleLogic(model, convertedModel, convField, modelField);
                                break;
                            case COMPOSITE:
                                compositeLogic(model, convertedModel, convField, modelField);
                                break;
                            case COLLECTION:
                                collectionLogic(model, convertedModel, convField, modelField);
                                break;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                logger.info("No access to field " + convField.getName() + "\n" + e);
                throw new RuntimeException("No access to field " + convField.getName() + "\n" + e);
            }
        }
    }

    private PropertyType getPropertyType(ReferencedField modelFieldAnnotation) {
        PropertyType propertyType;
        if (modelFieldAnnotation != null) {
            propertyType = modelFieldAnnotation.type();
        } else {
            propertyType = PropertyType.SIMPLE;
        }
        return propertyType;
    }

    private void simpleLogic(Object model, Object convertedModel, Field convField, Field modelField) throws IllegalAccessException {
        try {
            convField.set(convertedModel, modelField.get(model));
        } catch (IllegalArgumentException e) {
            throwReferencedTypeExc("COMPOSITE", "COLLECTION", "SIMPLE", modelField, convField);
        }
    }

    private void compositeLogic(Object model, Object convertedModel, Field convField, Field modelField) throws IllegalAccessException {
        try {
            convField.set(convertedModel,
                    convert(modelField.get(model),
                            convField.getType(),
                            convField.get(convertedModel)));
        } catch (IllegalArgumentException e) {
            throwReferencedTypeExc("SIMPLE", "COLLECTION", "COMPOSITE", modelField, convField);
        }
    }

    private void collectionLogic(Object model, Object convertedModel, Field convField, Field modelField) throws IllegalAccessException {
        boolean isArray = modelField.get(model).getClass().isArray();
        boolean isCollection = Collection.class.isAssignableFrom(modelField.get(model).getClass());
        boolean isMap = Map.class.isAssignableFrom(modelField.get(model).getClass());

        if (!isArray && !isCollection && !isMap) {
            throwReferencedTypeExc("SIMPLE", "COMPOSITE", "COLLECTION", modelField, convField);
        }

        if (isCollection || isMap) {
            convField.set(convertedModel, createNewInstance(null, convField.getAnnotation(ReferencedField.class).thisCollectionType()));
            if (isCollection) {
                isCollectionLogic(modelField, model, convField, convertedModel);
            }
            if (isMap) {
                isMapLogic(modelField, model, convField, convertedModel);
            }
        }

        if (isArray) {
            isArrayLogic();
        }
    }

    private void isMapLogic(Field modelField, Object model, Field convField, Object convertedModel) throws IllegalAccessException {
        //TODO
    }

    private void isArrayLogic() {
        //TODO
    }

    private void isCollectionLogic(Field modelField, Object model, Field convField, Object convertedModel) throws IllegalAccessException {
        Collection collectionModel = (Collection) modelField.get(model);
        Collection collectionConv = (Collection) convField.get(convertedModel);
        for (Object object : collectionModel) {
            collectionConv.add(convert(object,
                    convField.getAnnotation(ReferencedField.class).thisContainsClass(),
                    null));
        }
        convField.set(convertedModel, collectionConv);
    }

    private Map<String, Field> fillFieldMap(Field[] convFields) {
        Map<String, Field> modelFieldMap = new HashMap<>();
        for (Field modelField : convFields) {
            ReferencedField referencedField = modelField.getAnnotation(ReferencedField.class);
            if (referencedField != null && !referencedField.name().isEmpty()) {
                modelFieldMap.put(referencedField.name(), modelField);
            } else {
                modelFieldMap.put(modelField.getName(), modelField);
            }
        }
        return modelFieldMap;
    }

    private Object createNewInstance(Object convertedModel, Class toClazz) {
        if (convertedModel == null) {
            try {
                return toClazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                throw new ClassConstructorException("Object create failed! [Mapper.createNewInstance] Class: [" + toClazz + "]. " + e.getMessage());
            } catch (InvocationTargetException e) {
                logger.info("InvocationTargetException :) \n" + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else {
            return convertedModel;
        }
    }

    private void validate(Class modelClazz, Class toClazz) {
        if (modelClazz.getAnnotation(Convertible.class) == null
                || toClazz.getAnnotation(Convertible.class) == null) {
            throw new IsNotConvertibleException("Class is not convertible. " + modelClazz.getSimpleName() +
                    ": " + modelClazz.getAnnotation(Convertible.class) + ", " +
                    toClazz.getSimpleName() + ": " + toClazz.getAnnotation(Convertible.class) +
                    ". Try to add annotation [@Convertible] if missing.");
        }
    }

    private void throwReferencedTypeExc(String opOne, String opTwo, String as, Field modelField, Field convField) {
        throw new WrongReferencedTypeException("Seems like you trying to convert [" + opOne + "] or [" + opTwo + "] property as [" + as + "]. " +
                "Check [@ReferencedField] annotations: " + modelField.getDeclaringClass().getSimpleName() +
                ": " + modelField.getName() + ", " + convField.getDeclaringClass().getSimpleName() + ": " + convField.getName());

    }

    //convertedModel is optional (null would be fine)
    public Object convert(Object model, Class toClazz, Object convertedModel) {

        Class modelClazz = model.getClass();

        if (functionsMap.get(modelClazz.getSimpleName()) != null) {
            return setPrimitive(model, modelClazz);
        }

        convertedModel = createNewInstance(convertedModel, toClazz);

        validate(modelClazz, toClazz);

        Field[] modelFields = modelClazz.getDeclaredFields();
        Field[] convFields = toClazz.getDeclaredFields();
        Map<String, Field> convFieldMap = fillFieldMap(convFields);

        connectFields(modelFields, convFieldMap, model, convertedModel);

        return convertedModel;
    }
}
