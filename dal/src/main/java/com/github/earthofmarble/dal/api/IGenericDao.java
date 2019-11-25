package com.github.earthofmarble.dal.api;

import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.CommonFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IGenericDao <T, PK extends Serializable> {

    List<T> readByPk(PK primaryKey);
    List<T> readAll(IFilter filter);
    void create(T model);
    void merge(T model);
    void delete(T model);

}
