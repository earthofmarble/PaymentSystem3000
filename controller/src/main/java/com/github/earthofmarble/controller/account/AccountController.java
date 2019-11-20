package com.github.earthofmarble.controller.account;

import com.github.earthofmarble.controller.AbstractController;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.dto.account.moneytransfer.MoneyOperationDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.service.api.account.IAccountService;
import com.github.earthofmarble.service.api.payment.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by earthofmarble on Nov, 2019
 */
@RestController
@RequestMapping(value = "{ownerId}/accounts/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                                               consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AccountController extends AbstractController {

    private IAccountService accountService;
    private IPaymentService paymentService;

    @Autowired
    public AccountController(IAccountService accountService, IPaymentService paymentService) {
        this.accountService = accountService;
        this.paymentService = paymentService;
    }

    @GetMapping  //tested
    public List<AccountInfoDto> getUserAccounts(@PathVariable(value = "ownerId") int ownerId) {
        return castModelDtoList(accountService.getUserAccounts(ownerId, AccountInfoDto.class), AccountInfoDto.class);
    }

    @GetMapping(value = "/{accountId}") //not tested
    public AccountExtendedDto getAccount(@PathVariable(value = "accountId") int accountId) {
        return (AccountExtendedDto) accountService.readById(accountId, AccountExtendedDto.class);
    }

    @PostMapping(value = "create-account")  //not tested //TODO move to admin controller
    public boolean createAccount(@RequestBody AccountExtendedDto account) {
        return accountService.create(account);
    }

    @PutMapping(value = "/{accountId}/transfer-money")  //not tested
    public PaymentDto transferMoney(@PathVariable(value = "accountId") int accountId,
                                    @RequestBody MoneyOperationDto transferDto){
        AccountInfoDto senderAccount = transferDto.getSenderAccount();
//    todo    checkAuthority(senderAccount.getId())
        PaymentDto payment = accountService.orderMoney(senderAccount.getNumber(),
                                         transferDto.getReceiverAccount().getNumber(),
                                         transferDto.getSum());
        paymentService.create(payment);
        return payment;
    }

    @PutMapping(value = "/fund-account")  //not tested
    public PaymentDto fundAccount(@RequestBody MoneyOperationDto fundDto){
        PaymentDto payment = accountService.fundAccount(fundDto.getReceiverAccount().getNumber(),
                                                        fundDto.getSum(), fundDto.getCurrency());
        paymentService.create(payment);
        return payment;
    }

    @PutMapping(value = "/withdraw-money")  //not tested
    public PaymentDto withdrawMoney(@RequestBody MoneyOperationDto withdrawDto){
        //todo сделать подтверждение платежа через письмо на почту
        PaymentDto payment = accountService.withdrawMoney(withdrawDto.getSenderAccount(), withdrawDto.getSum());
        paymentService.create(payment);
        return payment;
    }

    @PutMapping(value = "/{accountId}/change-lock") //not tested
    public boolean changeLockState(@PathVariable(value = "accountId") int accountId,
                                   @RequestBody AccountInfoDto account) {
//       todo checkAuthority(accountId);
        return accountService.changeLockoutState(account.getNumber(), account.getLocked());
    }


}
