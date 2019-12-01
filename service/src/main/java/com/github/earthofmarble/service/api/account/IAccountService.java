package com.github.earthofmarble.service.api.account;

import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.filter.impl.account.AccountFilter;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.service.api.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IAccountService extends IGenericService<Account, Integer> {

    /**
     *  This method is used in actual withdraw operations, so:
     *  @param accountDto is an account to withdraw from
     *  @param sum unconverted sum
     *  @return returns payment object to create in controller
     */
    PaymentDto withdrawMoney(AccountInfoDto accountDto, Double sum, CurrencyDto receiverCurrencyDto);

    /**
     *  This method is used in actual replenish operations, so:
     *  @param senderCurrencyDto is a currencyDTO parameter (unlike addMoney()) to be able to call this method from a controller.
     *                          "senderCurrency" means the currency of the place where the payment is made
     *  @return returns payment object to create in controller
     */
    PaymentDto fundAccount(String accountNumber, Double sum, CurrencyDto senderCurrencyDto);

    /**
     *  A transfer operation between two accounts
     *  @param sum unconverted sum
     *  @return returns payment object to create in controller
     */
    PaymentDto orderMoney(String senderAccountNumber, String receiverAccountNumber, Double sum);

    /**
     *  Reverse of the account's lockout status.
     *  @param lock true/false to set account locked/unlocked
     *  @return true if no errors occurred
     */
    boolean changeLockoutState(String accountNumber, Boolean lock);

}
