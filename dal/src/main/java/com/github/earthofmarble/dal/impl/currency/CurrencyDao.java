package com.github.earthofmarble.dal.impl.currency;

import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.dal.api.currency.ICurrencyDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Repository
public class CurrencyDao extends AbstractDao<Currency, Integer> implements ICurrencyDao {
}
