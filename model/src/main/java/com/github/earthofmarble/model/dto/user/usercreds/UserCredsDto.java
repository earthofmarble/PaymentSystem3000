package com.github.earthofmarble.model.dto.user.usercreds;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class UserCredsDto implements IDto {

    private Integer id;
    private String username;
    private String password;

    public UserCredsDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
