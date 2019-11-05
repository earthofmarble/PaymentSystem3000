package com.github.earthofmarble.dal.impl.account;

import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.dal.api.account.IAccountDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import com.github.earthofmarble.model.model.account.Account_;
import com.github.earthofmarble.model.model.user.User;
import com.github.earthofmarble.model.model.user.User_;
import com.github.earthofmarble.utility.defaultgraph.Function;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public class AccountDao extends AbstractDao<Account, Integer> implements IAccountDao {

    private void changeBalance(boolean withdraw, Integer accountId, Double amount){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Account> update = criteriaBuilder.createCriteriaUpdate(Account.class);
        Root<Account> root = update.from(Account.class);
        Expression<Double> balance = root.get(Account_.BALANCE);
        Expression<Double> updatedBalance;
        if (withdraw){
            updatedBalance = criteriaBuilder.diff(balance, amount);
        } else {
            updatedBalance = criteriaBuilder.sum(balance, amount);
        }
        update.set((Path<? super Double>) root.get(Account_.BALANCE), updatedBalance);
        update.where(criteriaBuilder.equal(root.get(Account_.ID), accountId));

        entityManager.createQuery(update).executeUpdate();
    }

    public List<Account> readByAccountNumber(String accountNumber){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get(Account_.NUMBER), accountNumber));

        return buildSelectQuery(Account.class, criteriaQuery, criteriaBuilder, root,
                                Function.READ_BATCH, predicates, fillOrderList(criteriaBuilder, root)); //READ_BATCH is set because there's no need to load extra parameters, like payments etc.
    }

    public List<Account> getUserAccounts(Integer userId){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        Join<Account, User> userJoin = root.join(Account_.OWNER);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(userJoin.get(User_.ID), userId));
        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.desc(root.get(Account_.BALANCE)));

        return buildSelectQuery(Account.class, criteriaQuery, criteriaBuilder, root,
                                Function.READ_BATCH, predicates, orderList);
    }

    public void withdrawMoney(Integer accountId, Double amount){
        changeBalance(true, accountId, amount);
    }

    public void addMoney(Integer accountId, Double amount){
        changeBalance(false, accountId, amount);
    }

}
