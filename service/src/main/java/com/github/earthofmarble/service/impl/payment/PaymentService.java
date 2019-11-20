package com.github.earthofmarble.service.impl.payment;

import com.github.earthofmarble.dal.api.payment.IPaymentDao;
import com.github.earthofmarble.dal.api.user.IUserCredsDao;
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

//    public void createPayment(Account senderAccount, Account receiverAccount, Double sum){
//        Currency senderCurrency = senderAccount.getCurrency();
//        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
//        Payment payment = new Payment(senderAccount, receiverAccount, sum, senderCurrency, currentTime);
//        paymentDao.create(payment);
//    }

}
