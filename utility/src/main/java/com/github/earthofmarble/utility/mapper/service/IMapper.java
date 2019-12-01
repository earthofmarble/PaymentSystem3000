package com.github.earthofmarble.utility.mapper.service;

/**
 * Created by earthofmarble on Nov, 2019
 */

public interface IMapper {

    /**
     * Converts model to dto and vice versa. All non-null model's fields will be placed to convertedModel's fields. !Important! Proxy!=null !!!
     * Make sure all @Convertible and @ReferencedField annotations placed
     * @param model model to convert
     * @param toClazz ClassName.class to convert given model
     * @param convertedModel optional parameter (if [null] - a new instance will be created) (if [not null] - new values will be inserted to given object's fields)
     * @return convertedModel
     */
    Object convert(Object model, Class toClazz, Object convertedModel);

}
