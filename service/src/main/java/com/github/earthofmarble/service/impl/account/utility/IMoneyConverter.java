package com.github.earthofmarble.service.impl.account.utility;

import com.github.earthofmarble.model.model.currency.Currency;

/**
 * Created by earthofmarble on Nov, 2019
 */

public interface IMoneyConverter {

    Double convertMoney(Currency senderCurrency, Currency receiverCurrency, Double amount);

}
