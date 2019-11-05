package com.github.earthofmarble.dal.api.user;

import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.dal.api.IGenericDao;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IUserCredsDao extends IGenericDao<UserCreds, Integer> {

    List<UserCreds> getByLogin(String username);

}
