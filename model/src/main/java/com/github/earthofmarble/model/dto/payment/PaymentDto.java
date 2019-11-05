package com.github.earthofmarble.model.dto.payment;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import java.sql.Timestamp;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class PaymentDto implements IDto {

    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AccountExtendedDto sender;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AccountExtendedDto receiver;
    private Double amount;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private CurrencyDto currency;
    private Timestamp date;

    public PaymentDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountExtendedDto getSender() {
        return sender;
    }

    public void setSender(AccountExtendedDto sender) {
        this.sender = sender;
    }

    public AccountExtendedDto getReceiver() {
        return receiver;
    }

    public void setReceiver(AccountExtendedDto receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}