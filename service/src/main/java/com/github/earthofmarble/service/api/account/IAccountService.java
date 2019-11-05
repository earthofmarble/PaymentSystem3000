package com.github.earthofmarble.service.api.account;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IAccountService extends IGenericService<Account, Integer> {

    List<IDto> getUserAccounts(Integer userId, Class toDtoClazz); //AccountInfoDto.class
    Account withdrawMoney(String accountNumber, Double sum);
    Account addMoney(String accountNumber, Double sum, Currency senderCurrency);
    boolean orderMoney(String senderAccountNumber, String receiverAccountNumber, Double sum);
    boolean changeBLockState(String accountNumber, Boolean lock);

}
