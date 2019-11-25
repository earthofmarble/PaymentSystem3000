package com.github.earthofmarble.service.api;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.CommonFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IGenericService<T, PK extends Serializable> {
    /**
     * Simple [readAll] operation, the only difference is in:
     * @param filter - clauses to filter database selection results
     * @param convertToDtoClazz - dto's class for conversion
     * @return list of dtos (accounts, users, ...)
     */
    List<IDto> readWithFilter(IFilter filter, Class convertToDtoClazz);
    /**
     * Simple read by primary key operation
     * @param convertToDtoClazz - dto's class for conversion
     * @return dto (account, user, ...)
     */
    IDto readById(PK primaryKey, Class convertToDtoClazz);
    boolean create(IDto dto);
    boolean update(IDto dto);
    boolean delete(IDto dto);

}
