package com.github.earthofmarble.service.api;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.filter.AbstractFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IGenericService<T, PK extends Serializable> {

    List<IDto> readWithFilter(AbstractFilter filter, Class dtoClazz);
    IDto readById(PK primaryKey, Class dtoClazz);
    boolean create(IDto dto);
    void update(IDto dto);
    void delete(IDto dto);

}
