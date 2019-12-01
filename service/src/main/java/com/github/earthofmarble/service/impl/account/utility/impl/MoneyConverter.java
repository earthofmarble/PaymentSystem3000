package com.github.earthofmarble.service.impl.account.utility.impl;

import com.github.earthofmarble.model.model.currency.Currency;
import org.springframework.stereotype.Component;
import com.github.earthofmarble.service.impl.account.utility.IMoneyConverter;
/**
 * Created by earthofmarble on Nov, 2019
 */
@Component
public class MoneyConverter implements IMoneyConverter {

    /**
     * just some conversion logic to minimize code.
     * @return converted value in BYN or to BYN currency, based on if toByn parameter set to true or false
     */
    private Double conversion(Currency currency, Double amount, boolean toByn) {
        Integer scale = currency.getScale();
        Double rate = currency.getRate();
        if (toByn) {
            return amount * (rate / scale);
        }
        return amount / (rate / scale);
    }

    /**
     * Conversion method to call from outside
     * @return final converted value
     */
    public Double convertMoney(Currency senderCurrency, Currency receiverCurrency, Double amount) {
        if (receiverCurrency==null || senderCurrency.getId().equals(receiverCurrency.getId())) {
            return amount;
        }
        amount = conversion(senderCurrency, amount, true);
        amount = conversion(receiverCurrency, amount, false);
        return amount;
    }

}
