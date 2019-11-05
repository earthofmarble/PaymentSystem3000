package com.github.earthofmarble.model.filter;

/**
 * Created by earthofmarble on Oct, 2019
 */

public abstract class AbstractFilter {

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
