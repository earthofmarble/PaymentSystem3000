package com.github.earthofmarble.dal.impl.payment;

import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.dal.api.payment.IPaymentDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Repository
public class PaymentDao extends AbstractDao<Payment, Integer> implements IPaymentDao {
}
