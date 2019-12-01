package com.github.earthofmarble.model.filter;

/**
 * Created by earthofmarble on Nov, 2019
 */

public interface IFilter {
    Integer getFirstElement();
    Integer getPageSize();
    void setFirstElement(Integer firstElement);
    void setPageSize(Integer pageSize);
}
