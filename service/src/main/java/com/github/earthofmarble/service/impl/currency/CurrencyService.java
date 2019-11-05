package com.github.earthofmarble.service.impl.currency;

import com.github.earthofmarble.dal.api.IGenericDao;
import com.github.earthofmarble.dal.api.currency.ICurrencyDao;
import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.currency.ICurrencyService;
import com.github.earthofmarble.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
@Transactional
public class CurrencyService extends AbstractService<Currency, Integer> implements ICurrencyService {

    private ICurrencyDao currencyDao;

    @Autowired
    private CurrencyService(ICurrencyDao currencyDao){
        super(currencyDao);
        this.currencyDao = currencyDao;
    }

    private List<Currency> convertListFromDto(List<CurrencyDto> dtos) {
        List<Currency> models = new ArrayList<>();
        for (IDto dto : dtos) {
            models.add(convertToModel(dto));
        }
        return models;
    }

    private Currency convertToModel(IDto dto) {
        return (Currency) mapper.convert(dto, Currency.class, null);
    }

    public boolean updateCurrencies(List<CurrencyDto> currencyDtos){
        List<Currency> currencies = convertListFromDto(currencyDtos);
        currencies.forEach(currency -> currencyDao.merge(currency));
        return true;
    }

}
