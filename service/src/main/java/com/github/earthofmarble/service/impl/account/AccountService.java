package com.github.earthofmarble.service.impl.account;

import com.github.earthofmarble.dal.api.account.IAccountDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.dto.currency.CurrencyDto;
import com.github.earthofmarble.model.dto.payment.PaymentDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.model.model.payment.Operation;
import com.github.earthofmarble.service.api.account.IAccountService;
import com.github.earthofmarble.service.impl.AbstractService;
import com.github.earthofmarble.service.impl.account.utility.IMoneyConverter;
import com.github.earthofmarble.utility.exception.NotEnoughMoneyException;
import com.github.earthofmarble.utility.exception.RecordAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
@Transactional
public class AccountService extends AbstractService<Account, Integer> implements IAccountService {

    private IAccountDao accountDao;
    private IMoneyConverter moneyConverter;

    @Autowired
    private AccountService(IAccountDao accountDao, IMoneyConverter moneyConverter) {
        super(accountDao);
        this.accountDao = accountDao;
        this.moneyConverter = moneyConverter;
    }

    /**
     * Throws an exception if account hasn't enough money to complete an operation:
     * @param account - account-sender,
     * @param amount - requested money amount
     */
    private void tryBalance(Account account, Double amount) {
        if (account.getBalance() < amount) {
            throw new NotEnoughMoneyException("Not enough money to make a transfer. Balance: [" + account.getBalance() + "]");
        }
    }

    /**
     * Forms a payment object based on:
     * @param senderAccount - account-sender (must be null on replenishment operation),
     * @param receiverAccount - account-receiver (must be null on withdrawal operation),
     * @return payment object
     */
    private PaymentDto createPaymentDto(Account senderAccount, Account receiverAccount, Double sum,
                                        Currency currency, Operation operation){
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        AccountExtendedDto senderDto = null;
        AccountExtendedDto receiverDto = null;
        if (senderAccount!=null){
            senderDto = (AccountExtendedDto) mapper.convert(senderAccount, AccountExtendedDto.class, null);
        }
        if (receiverAccount!=null){
            receiverDto = (AccountExtendedDto) mapper.convert(receiverAccount, AccountExtendedDto.class, null);
        }
        CurrencyDto currencyDto = (CurrencyDto) mapper.convert(currency, CurrencyDto.class, null);
        return new PaymentDto(senderDto, receiverDto, sum, currencyDto, currentTime, operation);
    }

    /**
     * This method was designed to use only in transfer operations so:
     * @param senderCurrency - a currency parameter (not a dto)
     * @return returns an account for transfer purposes
     */
    private Account addMoney(String accountNumber, Double unconvertedSum, Currency senderCurrency) {
        Account account = getAccountUsingNumber(accountNumber);
        unconvertedSum = moneyConverter.convertMoney(senderCurrency, account.getCurrency(), unconvertedSum);
        accountDao.addMoney(account.getId(), unconvertedSum);
        return account;
    }

    /**
     * This method was designed to use only in transfer operations so:
     * @param sum - already converted amount of money
     * @return returns an account for transfer purposes
     */
    private Account subtractMoney(String accountNumber, Double sum) {
        Account account = getAccountUsingNumber(accountNumber);
        tryBalance(account, sum);
        accountDao.withdrawMoney(account.getId(), sum);
        return account;
    }

    /**
     *  This method is used in actual replenish operations, so:
     *  @param senderCurrencyDto is a currencyDTO parameter (unlike addMoney()) to be able to call this method from a controller.
     *                          "senderCurrency" means the currency of the place where the payment is made
     *  @return returns payment object to create in controller
     */
    public PaymentDto fundAccount(String accountNumber, Double sum, CurrencyDto senderCurrencyDto) {
        Currency currency = (Currency) mapper.convert(senderCurrencyDto, Currency.class, null);
        Account account = addMoney(accountNumber, sum, currency);
        return createPaymentDto(null, account, sum, account.getCurrency(), Operation.REPLENISHMENT);
    }

    /**
     *  This method is used in actual withdraw operations, so:
     *  @param accountDto is an account to withdraw from
     *  @param sum unconverted sum
     *  @return returns payment object to create in controller
     */
    public PaymentDto withdrawMoney(AccountInfoDto accountDto, Double sum) {
       Account account = subtractMoney(accountDto.getNumber(), sum);
       return createPaymentDto(account, null, sum, account.getCurrency(), Operation.WITHDRAWAL);
    }

    /**
     *  A transfer operation between two accounts
     *  @param sum unconverted sum
     *  @return returns payment object to create in controller
     */
    public PaymentDto orderMoney(String senderAccountNumber, String receiverAccountNumber, Double sum) {
        Account senderAccount = subtractMoney(senderAccountNumber, sum);
        Account receiverAccount = addMoney(receiverAccountNumber, sum, senderAccount.getCurrency());
        return createPaymentDto(senderAccount, receiverAccount, sum, senderAccount.getCurrency(), Operation.TRANSFER);
//        paymentService.createPayment(senderAccount, receiverAccount, sum); //TODO теперь будет две транзакции, одна переводит деньги, другая - выдает чек (в контроллере)
    }

    /**
     *  Reverse of the account's lockout status.
     *  @param lock true/false to set account locked/unlocked
     *  @return true if no errors occurred
     */
    public boolean changeLockoutState(String accountNumber, Boolean lock) {
        Account account = getAccountUsingNumber(accountNumber);
        account.setLocked(lock);
        accountDao.merge(account);
        return true;
    }

    /**
     * Simple [readById] operation, but instead of filtering by id, accounts are filtered by account number:
     * @param accountNumber -
     * @return list of dto's (account's)
     */
    private Account getAccountUsingNumber(String accountNumber) {
        List<Account> accounts = accountDao.readByAccountNumber(accountNumber);
        checkSingleListSize(accounts);
        return accounts.get(0);
    }

    /**
     * Simple [readAll] operation, the only difference is in:
     * @param userId - clause to filter accounts
     * @return list of dto's (account's)
     */
    public List<IDto> getUserAccounts(Integer userId, Class toDtoClazz) {
        List<Account> accounts = accountDao.getUserAccounts(userId);
        return convertListToDto(accounts, toDtoClazz);
    }

    @Override
    public boolean create(IDto dto) {
        AccountExtendedDto accountDto = (AccountExtendedDto) dto;
        List<Account> accounts = accountDao.readByAccountNumber(accountDto.getNumber());
        if (!accounts.isEmpty()){
            throw new RecordAlreadyExistsException("An account with number ["+ accountDto.getNumber() + "] already exists!");
        }
        return super.create(dto);
    }
}
