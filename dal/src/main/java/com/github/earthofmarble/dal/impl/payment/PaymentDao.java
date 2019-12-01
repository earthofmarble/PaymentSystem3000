package com.github.earthofmarble.dal.impl.payment;

import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.CommonFilter;
import com.github.earthofmarble.model.filter.impl.account.AccountFilter;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.account.Account_;
import com.github.earthofmarble.model.model.payment.Payment;
import com.github.earthofmarble.dal.api.payment.IPaymentDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import com.github.earthofmarble.model.model.payment.Payment_;
import com.github.earthofmarble.model.model.user.User;
import com.github.earthofmarble.model.model.user.User_;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
@Repository
public class PaymentDao extends AbstractDao<Payment, Integer> implements IPaymentDao {

    public List<Payment> readAccountPayments(Integer accountId, IFilter filter){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Payment.class);

        Root<Payment> root = criteriaQuery.from(Payment.class);
        Join<Payment, Account> senderJoin = root.join(Payment_.SENDER);
        Join<Payment, Account> receiverJoin = root.join(Payment_.RECEIVER);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(senderJoin.get(Account_.ID), accountId));
        predicates.add(criteriaBuilder.equal(receiverJoin.get(Account_.ID), accountId));

        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.desc(root.get(Payment_.DATE)));

        return buildSelectQuery(Account.class, criteriaQuery, criteriaBuilder, root,
                Function.READ_BATCH, predicates, orderList, filter, false);
    }

}
