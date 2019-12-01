package com.github.earthofmarble.dal.api.payment;

import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.dal.api.IGenericDao;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IPaymentDao extends IGenericDao<Payment, Integer> {

    List<Payment> readAccountPayments(Integer accountId, IFilter filter);

}
