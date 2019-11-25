package com.github.earthofmarble.model.filter.impl;

import com.github.earthofmarble.model.filter.IFilter;

/**
 * Created by earthofmarble on Oct, 2019
 */

public class CommonFilter implements IFilter {

    private Integer firstElement;
    private Integer pageSize;

    public Integer getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(Integer firstElement) {
        this.firstElement = firstElement;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
