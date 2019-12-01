package com.github.earthofmarble.service.impl.payment;

import com.github.earthofmarble.dal.api.payment.IPaymentDao;
import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.account.AccountFilter;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.service.api.payment.IPaymentService;
import com.github.earthofmarble.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
@Transactional
public class PaymentService extends AbstractService<Payment, Integer> implements IPaymentService {

    private IPaymentDao paymentDao;

    @Autowired
    private PaymentService(IPaymentDao paymentDao){
        super(paymentDao);
        this.paymentDao = paymentDao;
    }

    @Override
    public PaymentDto create(PaymentDto paymentDto){
      Payment payment = (Payment) mapper.convert(paymentDto, Payment.class, null);
      paymentDao.create(payment);
      paymentDto.setId(payment.getId());
      return paymentDto;
    }

    public List<IDto> getPaymentsHistory(Integer accountId, IFilter filter, Class dtoClazz) {
        List<Payment> payments = paymentDao.readAccountPayments(accountId, filter);
        return convertListToDto(payments, dtoClazz);
    }

}
