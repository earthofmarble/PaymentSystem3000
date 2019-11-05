package com.github.earthofmarble.dal.api.account;

import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.dal.api.IGenericDao;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IAccountDao extends IGenericDao<Account, Integer> {

    List<Account> readByAccountNumber(String accountNumber);
    List<Account> getUserAccounts(Integer userId);
    void withdrawMoney(Integer accountId, Double amount);
    void addMoney(Integer accountId, Double amount);

}
