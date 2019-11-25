package com.github.earthofmarble.model.filter.impl.user;

import com.github.earthofmarble.model.filter.impl.CommonFilter;

/**
 * Created by earthofmarble on Oct, 2019
 */
public class UserFilter extends CommonFilter {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
