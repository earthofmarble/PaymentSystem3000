package com.github.earthofmarble.model.dto.account.moneytransfer;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;

/**
 * Created by earthofmarble on Nov, 2019
 */

public class MoneyOperationDto implements IDto {

    private AccountInfoDto senderAccount;
    private AccountInfoDto receiverAccount;
    private Double sum;
    private CurrencyDto currency;

    public AccountInfoDto getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(AccountInfoDto senderAccount) {
        this.senderAccount = senderAccount;
    }

    public AccountInfoDto getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(AccountInfoDto receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
