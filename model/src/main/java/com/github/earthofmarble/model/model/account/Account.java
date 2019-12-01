package com.github.earthofmarble.model.model.account;

import com.github.earthofmarble.model.model.IModel;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.model.model.user.User;
import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraph;
import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraphs;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "account")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "accountInfoGraph", attributeNodes = {@NamedAttributeNode(value = Account_.CURRENCY)}),
        @NamedEntityGraph(name = "accountExtendedGraph", attributeNodes = {@NamedAttributeNode(value = Account_.CURRENCY),
                                                                           @NamedAttributeNode(value = Account_.OWNER)})
})
@DefaultGraphs(value = {
        @DefaultGraph(function = Function.READ_BATCH, name = "accountInfoGraph"),
        @DefaultGraph(function = Function.READ_SINGLE, name = "accountExtendedGraph")
})
@Convertible
public class Account implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private User owner;
    @Column
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Currency currency;
    @Column
    private Double balance;
    @Column
    private String number;
    @Column(name = "is_locked")
    private Boolean isLocked;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @ReferencedField(type = PropertyType.COLLECTION, thisContainsClass = Payment.class)
    private List<Payment> sentPayments;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @ReferencedField(type = PropertyType.COLLECTION, thisContainsClass = Payment.class)
    private List<Payment> receivedPayments;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public List<Payment> getSentPayments() {
        if (sentPayments==null){
            return new ArrayList<>();
        }
        return sentPayments;
    }

    public void setSentPayments(List<Payment> sentPayments) {
        this.sentPayments = sentPayments;
    }

    public List<Payment> getReceivedPayments() {
        if (receivedPayments==null){
            return new ArrayList<>();
        }
        return receivedPayments;
    }

    public void setReceivedPayments(List<Payment> receivedPayments) {
        this.receivedPayments = receivedPayments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

