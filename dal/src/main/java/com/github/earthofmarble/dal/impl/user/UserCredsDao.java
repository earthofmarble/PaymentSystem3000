package com.github.earthofmarble.dal.impl.user;

import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.dal.impl.AbstractDao;
import com.github.earthofmarble.model.model.user.UserCreds_;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Repository
@SuppressWarnings("unchecked")
public class UserCredsDao extends AbstractDao<UserCreds, Integer> implements IUserCredsDao {

    public List<UserCreds> getByLogin(String username){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(UserCreds.class);
        Root<UserCreds> root = criteriaQuery.from(UserCreds.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get(UserCreds_.USERNAME), username));

        return buildSelectQuery(UserCreds.class, criteriaQuery, criteriaBuilder, root, Function.NONE,
                predicates, fillOrderList(criteriaBuilder, root), null, true);
    }

}
