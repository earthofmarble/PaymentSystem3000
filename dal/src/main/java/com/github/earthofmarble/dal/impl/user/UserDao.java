package com.github.earthofmarble.dal.impl.user;

import com.github.earthofmarble.model.filter.AbstractFilter;
import com.github.earthofmarble.model.filter.user.UserFilter;
import com.github.earthofmarble.model.model.user.User;
import com.github.earthofmarble.dal.api.user.IUserDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import com.github.earthofmarble.model.model.user.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
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
public class UserDao extends AbstractDao<User, Integer> implements IUserDao {

    @Override
    protected List<Predicate> fillPredicates(AbstractFilter filter, CriteriaBuilder criteriaBuilder, Root userRoot){
        List<Predicate> predicates = new ArrayList<>();

        String firstName = "";
        String lastName = "";

        UserFilter userFilter = (UserFilter) filter;

        if (userFilter.getFirstName()!=null && !userFilter.getFirstName().isEmpty()){
           firstName = userFilter.getFirstName();
        }
        if (userFilter.getLastName()!=null && !userFilter.getLastName().isEmpty()){
           lastName = userFilter.getLastName();
        }

        predicates.add(criteriaBuilder.like(userRoot.get(User_.firstName), "%" + firstName + "%"));
        predicates.add(criteriaBuilder.like(userRoot.get(User_.lastName), "%" + lastName + "%"));

        return predicates;
    }

    @Override
    protected List<Order> fillOrderList(CriteriaBuilder criteriaBuilder, Root root) {
        List<Order> orderList = new ArrayList();
        orderList.add(criteriaBuilder.asc(root.get(User_.lastName)));
        orderList.add(criteriaBuilder.asc(root.get(User_.firstName)));
        return orderList;
    }
}
