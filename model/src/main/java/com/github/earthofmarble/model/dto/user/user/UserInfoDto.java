package com.github.earthofmarble.model.dto.user.user;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.model.user.UserRole;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;

import java.util.Objects;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class UserInfoDto implements IDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
