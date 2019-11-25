package com.github.earthofmarble.service.api.payment;

import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.service.api.IGenericService;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IPaymentService extends IGenericService<Payment, Integer> {
    /**
     * This method is needed to create a payment than return a "payment check" otherwise, if we used regular "create()" method,
     *                                                                                      our "check" would not have an id
     * @param paymentDto payment dto, which already has all parameters except id
     * @return returns the same dto, but now it has an id
     */
    PaymentDto create(PaymentDto paymentDto);

}
