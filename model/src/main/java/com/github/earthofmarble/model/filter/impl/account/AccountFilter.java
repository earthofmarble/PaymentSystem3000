package com.github.earthofmarble.model.filter.impl.account;

import com.github.earthofmarble.model.filter.impl.CommonFilter;

/**
 * Created by earthofmarble on Oct, 2019
 */
public class AccountFilter extends CommonFilter {

    private Integer userId;
    private String name;
    private String number;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
