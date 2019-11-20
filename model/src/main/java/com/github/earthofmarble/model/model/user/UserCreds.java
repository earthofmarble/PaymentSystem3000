package com.github.earthofmarble.model.model.user;

import com.github.earthofmarble.model.model.IModel;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "user_creds")
@Convertible
public class UserCreds implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "userCreds", fetch = FetchType.LAZY)
    private User user;

    public UserCreds() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreds)) return false;
        UserCreds userCreds = (UserCreds) o;
        return getId().equals(userCreds.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
