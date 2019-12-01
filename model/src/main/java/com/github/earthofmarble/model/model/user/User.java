package com.github.earthofmarble.model.model.user;

import com.github.earthofmarble.model.model.IModel;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.account.Account_;
import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraph;
import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraphs;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "user")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "userInfoGraph"),
        //TODO может быть аккаунты не нужны для получения полной инфы о юзере, потому что, выгружаются сразу все аккаунты, насколько я понимаю, пагинацию на егерь лоад нельзя поставить. нужно подумать.
        @NamedEntityGraph(name = "userExtendedGraph", attributeNodes = {@NamedAttributeNode(value = User_.ACCOUNTS,
                                                                                            subgraph = "userAccountSubgraph")},
                          subgraphs = @NamedSubgraph(name = "userAccountSubgraph",
                                                     attributeNodes = @NamedAttributeNode(value = Account_.CURRENCY))),
        @NamedEntityGraph(name = "userProfileGraph", attributeNodes = {@NamedAttributeNode(value = User_.USER_CREDS)})
})
@DefaultGraphs(value = {
        @DefaultGraph(function = Function.READ_BATCH, name = "userInfoGraph"),
        @DefaultGraph(function = Function.READ_SINGLE, name = "userExtendedGraph")
})
@Convertible
public class User implements IModel {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "email")
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private UserCreds userCreds;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @ReferencedField(type = PropertyType.COLLECTION, thisContainsClass = Account.class)
    private List<Account> accounts;

    public User() {
    }

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

    public UserCreds getUserCreds() {
        return userCreds;
    }

    public void setUserCreds(UserCreds userCreds) {
        this.userCreds = userCreds;
    }

    public List<Account> getAccounts() {
        if (accounts==null){
            return new ArrayList<>();
        }
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
