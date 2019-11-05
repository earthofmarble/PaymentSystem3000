package com.github.earthofmarble.utility.mapper.annotation;

import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReferencedField {
    String name() default "";

    PropertyType type() default PropertyType.SIMPLE;

    Class thisCollectionType() default ArrayList.class;

    Class thisContainsClass() default void.class;
}
