package com.github.earthofmarble.service.impl.account;

import com.github.earthofmarble.dal.api.account.IAccountDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.account.IAccountService;
import com.github.earthofmarble.service.api.payment.IPaymentService;
import com.github.earthofmarble.service.impl.AbstractService;
import com.github.earthofmarble.utility.exception.NotEnoughMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
@Transactional
public class AccountService extends AbstractService<Account, Integer> implements IAccountService {

    private IAccountDao accountDao;
    private IPaymentService paymentService;

    @Autowired
    private AccountService(IAccountDao accountDao, IPaymentService paymentService) {
        super(accountDao);
        this.accountDao = accountDao;
        this.paymentService = paymentService;
    }

    private Account getAccount(String accountNumber) {
        List<Account> accounts = accountDao.readByAccountNumber(accountNumber);
        checkSingleListSize(accounts);
        return accounts.get(0);
    }

    private void checkBalance(Account account, Double amount) {
        if (account.getBalance() < amount) {
            throw new NotEnoughMoneyException("Not enough money to make a transfer. Balance: [" + account.getBalance() + "]");
        }
    }

    private Double convertLogics(Currency currency, Double amount, boolean toByn) {
        Integer scale = currency.getScale();
        Double rate = currency.getRate();
        if (toByn) {
            return amount * (rate / scale);
        }
        return amount / (rate / scale);
    }

    private Double convertMoney(Currency senderCurrency, Currency receiverCurrency, Double amount) {
        if (senderCurrency.getId().equals(receiverCurrency.getId())) {
            return amount;
        }
        amount = convertLogics(senderCurrency, amount, true);
        amount = convertLogics(receiverCurrency, amount, false);

        return amount;
    }

    public List<IDto> getUserAccounts(Integer userId, Class toDtoClazz) {
        List<Account> accounts = accountDao.getUserAccounts(userId);
        return convertListToDto(accounts, toDtoClazz);
    }

    public Account withdrawMoney(String accountNumber, Double sum) {
        Account account = getAccount(accountNumber);
        checkBalance(account, sum);
        accountDao.withdrawMoney(account.getId(), sum);
        return account;
    }

    public Account addMoney(String accountNumber, Double sum, Currency senderCurrency) {
        Account account = getAccount(accountNumber);
        sum = convertMoney(senderCurrency, account.getCurrency(), sum);
        accountDao.addMoney(account.getId(), sum);
        return account;
    }

    public boolean orderMoney(String senderAccountNumber, String receiverAccountNumber, Double sum) {
        Account senderAccount = withdrawMoney(senderAccountNumber, sum);
        Account receiverAccount = addMoney(receiverAccountNumber, sum, senderAccount.getCurrency());
        paymentService.createPayment(senderAccount, receiverAccount, sum);
        return true;
    }

    public boolean changeBLockState(String accountNumber, Boolean lock) {
        Account account = getAccount(accountNumber);
        account.setLocked(lock);
        accountDao.merge(account);
        return true;
    }

}
