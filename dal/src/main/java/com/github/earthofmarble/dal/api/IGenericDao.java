package com.github.earthofmarble.dal.api;

import com.github.earthofmarble.model.filter.AbstractFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IGenericDao <T, PK extends Serializable> {

    List<T> readByPk(PK primaryKey);
    List<T> readWithFilter(AbstractFilter filter);
    void create(T model);
    void merge(T model);
    void delete(T model);

}
