package com.github.earthofmarble.service.api.account;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IAccountService extends IGenericService<Account, Integer> {

    List<IDto> getUserAccounts(Integer userId, Class toDtoClazz); //AccountInfoDto.class
    PaymentDto withdrawMoney(AccountInfoDto accountDto, Double sum);
    PaymentDto fundAccount(String accountNumber, Double sum, CurrencyDto senderCurrencyDto);
    PaymentDto orderMoney(String senderAccountNumber, String receiverAccountNumber, Double sum);
    boolean changeLockoutState(String accountNumber, Boolean lock);

}
