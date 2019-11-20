package com.github.earthofmarble.service.impl.user;

import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.dal.api.user.IUserDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.model.dto.user.user.UserProfileDto;
import com.github.earthofmarble.model.model.user.User;
import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.api.user.IUserService;
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
public class UserService extends AbstractService<User, Integer> implements IUserService {

    private IUserDao userDao;
    private IUserCredsDao userCredsDao;
    private IUserCredsService userCredsService;

    @Autowired
    private UserService(IUserDao userDao, IUserCredsDao userCredsDao, IUserCredsService userCredsService){
        super(userDao);
        this.userDao = userDao;
        this.userCredsDao = userCredsDao;
        this.userCredsService = userCredsService;
    }

    @Override
    public boolean create(IDto dto) {
        tryCastPossibilities(dto, UserProfileDto.class);
        UserProfileDto userDto = (UserProfileDto) dto;
        if (!userCredsService.tryLogin(userDto.getUserCreds().getUsername())){
            return false;
        }
        UserCreds userCreds = (UserCreds) mapper.convert(userDto.getUserCreds(), UserCreds.class, null);
        userDto.setUserCreds(null);
        userCredsDao.create(userCreds);
        User user = (User) mapper.convert(userDto, User.class, null);
        user.setId(userCreds.getId());
        user.setUserCreds(userCreds);
        userDao.create(user);
        return true;
    }

    @Override
    public void update(IDto dto) {
        tryCastPossibilities(dto, UserProfileDto.class);
        UserProfileDto userDto = (UserProfileDto) dto;
        List<User> users = userDao.readByPk(userDto.getId());
        checkSingleListSize(users);
        userDao.merge((User) mapper.convert(userDto, User.class, users.get(0)));
    }

    @Override
    public void delete(IDto dto) {
        tryCastPossibilities(dto, UserInfoDto.class);
        List<UserCreds> credsList = userCredsDao.readByPk(dto.getId());
        checkSingleListSize(credsList);
        userCredsDao.delete(credsList.get(0));
    }
}
