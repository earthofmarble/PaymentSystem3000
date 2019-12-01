package com.github.earthofmarble.controller.payment;

import com.github.earthofmarble.controller.AbstractController;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.filter.impl.CommonFilter;
import com.github.earthofmarble.service.api.payment.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by earthofmarble on Nov, 2019
 */
@RestController
@RequestMapping(value = "{ownerId}/accounts/{accountId}",
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class PaymentController extends AbstractController {

    private IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/payments")   //not tested
    public List<PaymentDto> getPaymentsHistory(@PathVariable(value = "accountId") int accountId,
                                               @RequestBody CommonFilter commonFilter) {
        //    todo    checkAuthority(ownerId)
        return castModelDtoList(paymentService.getPaymentsHistory(accountId, commonFilter, PaymentDto.class), PaymentDto.class);
    }

}
