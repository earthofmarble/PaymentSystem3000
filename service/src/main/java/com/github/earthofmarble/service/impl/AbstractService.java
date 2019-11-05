package com.github.earthofmarble.service.impl;

import com.github.earthofmarble.dal.api.IGenericDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.filter.AbstractFilter;
import com.github.earthofmarble.service.api.IGenericService;
import com.github.earthofmarble.utility.exception.ClassConstructorException;
import com.github.earthofmarble.utility.exception.InvalidRecordAmountReturnedException;
import com.github.earthofmarble.utility.exception.InvocationException;
import com.github.earthofmarble.utility.exception.NoDbRecordException;
import com.github.earthofmarble.utility.mapper.exception.WrongReferencedTypeException;
import com.github.earthofmarble.utility.mapper.service.Mapper;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractService<T, PK extends Serializable> implements IGenericService<T, PK> {

    @Autowired
    protected Mapper mapper;
    private IGenericDao genericDao;

    protected AbstractService(IGenericDao genericDao) {
        this.genericDao = genericDao;
    }

    private Class<T> getEntityType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private IDto createNewInstanceByClass(Class clazz) {
        try {
            return (IDto) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new ClassConstructorException("Couldn't load constructor for class " + clazz.getName() + "\n" + e.getMessage());
        } catch (InstantiationException e) {
            throw new ClassConstructorException("Couldn't use constructor of class: " + clazz.getName() +
                    ". Maybe class has no nullable constructor or given class is an interface/abstract/primitive" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ClassConstructorException("Access to constructor of " + clazz.getName() + " restricted" + "\n" + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ClassConstructorException("InvocationTargetException :) An attempt to create an object failed! \n" + e.getMessage());
        }
    }

    protected void tryCastPossibilities(IDto dto, Class clazz){
        if (!dto.getClass().getName().equals(clazz.getName())){
           throw new WrongReferencedTypeException("Dto of a wrong type received. Expected: ["+clazz.getName()+"], " +
                                                  "actual: ["+dto.getClass().getSimpleName()+"]");
        }
    }

    protected List<IDto> convertListToDto(List<T> models, Class dtoClazz) {
        List<IDto> dtos = new ArrayList<>();
        if (models.isEmpty()){
            dtos.add(createNewInstanceByClass(dtoClazz));
        }
        for (T model : models) {
            dtos.add(convertToDto(model, dtoClazz));
        }
        return dtos;
    }

    protected IDto convertToDto(T model, Class dtoClazz) {
        return (IDto) mapper.convert(model, dtoClazz, null);
    }

    protected void checkSingleListSize(List models){
        if (models.isEmpty()) {
            throw new NoDbRecordException("There is no such [" + getEntityType().getSimpleName() +"]");
        }
        if (models.size() > 1) {
            throw new InvalidRecordAmountReturnedException(models.size() + " [" + getEntityType().getSimpleName() +
                    "] records returned from database, while expected [1].");
        }
    }

    public List<IDto> readWithFilter(AbstractFilter filter, Class dtoClazz) {
        List<T> models = genericDao.readWithFilter(filter);
        return convertListToDto(models, dtoClazz);
    }

    public IDto readById(PK primaryKey, Class dtoClazz) {
        List<T> models = genericDao.readByPk(primaryKey);
        checkSingleListSize(models);
        return convertToDto(models.get(0), dtoClazz);
    }

    public boolean create(IDto dto) {
        genericDao.create(mapper.convert(dto, getEntityType(), null));
        return true;
    }

    public void update(IDto dto) {
        List<T> models = genericDao.readByPk(dto.getId());
        checkSingleListSize(models);
        genericDao.merge(mapper.convert(dto, getEntityType(), models.get(0)));
    }

    public void delete(IDto dto) {
        List<T> models = genericDao.readByPk(dto.getId());
        checkSingleListSize(models);
        genericDao.delete(models.get(0));
    }
}