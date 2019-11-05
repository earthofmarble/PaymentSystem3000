package com.github.earthofmarble.model.dto.account;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class AccountInfoDto implements IDto {

    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private UserInfoDto owner;
    private String name;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private CurrencyDto currency;
    private Double balance;
    private String number;
    private Boolean isLocked;

    public AccountInfoDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfoDto getOwner() {
        return owner;
    }

    public void setOwner(UserInfoDto owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
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
}

