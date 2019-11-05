package com.github.earthofmarble.service.impl.user;

import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.dal.api.user.IUserDao;
import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
public class UserCredsService extends AbstractService<UserCreds, Integer> implements IUserCredsService {

    private IUserCredsDao userCredsDao;

    @Autowired
    private UserCredsService(IUserCredsDao userCredsDao){
        super(userCredsDao);
        this.userCredsDao = userCredsDao;
    }

}
