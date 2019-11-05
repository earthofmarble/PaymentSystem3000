package com.github.earthofmarble.model.model.payment;

import com.github.earthofmarble.model.model.IModel;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "payment")
@Convertible
public class Payment implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Account sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Account receiver;
    @Column
    private Double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Currency currency;
    @Column
    private Timestamp date;

    public Payment() {
    }

    public Payment(Account sender, Account receiver, Double amount, Currency currency, Timestamp date) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
