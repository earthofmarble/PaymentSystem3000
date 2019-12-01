package com.github.earthofmarble.model.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;
import com.github.earthofmarble.utility.mapper.enumeration.PropertyType;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class AccountExtendedDto implements IDto {

    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private UserInfoDto owner;
    private String name;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private CurrencyDto currency;
    private Double balance;
    private String number;
    @JsonProperty(value="isLocked")
    private Boolean isLocked;
//    @ReferencedField(type = PropertyType.COLLECTION, thisContainsClass = PaymentDto.class)
//    private List<PaymentDto> sentPayments;
//    @ReferencedField(type = PropertyType.COLLECTION, thisContainsClass = PaymentDto.class)
//    private List<PaymentDto> receivedPayments;

    public AccountExtendedDto() {
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

    //    public List<PaymentDto> getSentPayments() {
//        if (sentPayments==null){
//            return new ArrayList<PaymentDto>();
//        }
//        return sentPayments;
//    }
//
//    public void setSentPayments(List<PaymentDto> sentPayments) {
//        this.sentPayments = sentPayments;
//    }
//
//    public List<PaymentDto> getReceivedPayments() {
//        if (receivedPayments==null){
//            return new ArrayList<PaymentDto>();
//        }
//        return receivedPayments;
//    }
//
//    public void setReceivedPayments(List<PaymentDto> receivedPayments) {
//        this.receivedPayments = receivedPayments;
//    }
}

