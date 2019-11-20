package com.github.earthofmarble.service.impl.user;

import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.dal.api.user.IUserDao;
import com.github.earthofmarble.model.dto.user.user.UserProfileDto;
import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.impl.AbstractService;
import com.github.earthofmarble.utility.exception.InvalidRecordAmountReturnedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Service
@Transactional
public class UserCredsService extends AbstractService<UserCreds, Integer> implements IUserCredsService {

    private IUserCredsDao userCredsDao;

    @Autowired
    private UserCredsService(IUserCredsDao userCredsDao){
        super(userCredsDao);
        this.userCredsDao = userCredsDao;
    }

    public boolean tryLogin(String login){
        List<UserCreds> credsList = userCredsDao.getByLogin(login);
        if (credsList.size()>1){
            throw new InvalidRecordAmountReturnedException(credsList.size() + " [" + UserCreds.class.getSimpleName() +
                                                           "] records returned from database, but expected 1.");
        }
        return credsList.isEmpty();
    }

    public UserProfileDto getUserByLogin(String login){
        List<UserCreds> credsList = userCredsDao.getByLogin(login);
        checkSingleListSize(credsList);
        return (UserProfileDto) mapper.convert(credsList.get(0).getUser(), UserProfileDto.class, null);
    }

}
