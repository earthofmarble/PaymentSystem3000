package com.github.earthofmarble.service.api.currency;

import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface ICurrencyService extends IGenericService<Currency, Integer> {

    boolean updateCurrencies(List<CurrencyDto> currencyDtos);

}
