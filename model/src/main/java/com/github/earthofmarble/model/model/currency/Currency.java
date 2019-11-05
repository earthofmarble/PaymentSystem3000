package com.github.earthofmarble.model.model.currency;

import com.github.earthofmarble.model.model.IModel;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "currency")
@Convertible
public class Currency implements IModel {
    @Id
    @Column(name = "currency_bank_id", nullable = false)
    private Integer id;
    @Column(name = "abbreviation")
    private String abbreviation;
    @Column(name = "name")
    private String name;
    @Column(name = "scale")
    private Integer scale;
    @Column(name = "rate")
    private Double rate;
    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private List<Account> accounts;
    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private List<Payment> payments;

    public Currency() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", scale=" + scale +
                ", rate=" + rate +
                ", accounts=" + accounts +
                ", payments=" + payments +
                '}';
    }
}
