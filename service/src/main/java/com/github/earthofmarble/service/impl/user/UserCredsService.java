package com.github.earthofmarble.service.impl.user;

import com.github.earthofmarble.dal.api.user.IUserCredsDao;
import com.github.earthofmarble.dal.api.user.IUserDao;
import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.model.dto.user.user.UserProfileDto;
import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.impl.AbstractService;
import com.github.earthofmarble.utility.exception.InvalidRecordAmountReturnedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserCredsService(IUserCredsDao userCredsDao, PasswordEncoder passwordEncoder){
        super(userCredsDao);
        this.userCredsDao = userCredsDao;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean tryUsername(String login){
        List<UserCreds> credsList = userCredsDao.getByLogin(login);
        if (credsList.size()>1){
            throw new InvalidRecordAmountReturnedException(credsList.size() + " [" + UserCreds.class.getSimpleName() +
                                                           "] records returned from database, but expected 1.");
        }
        return credsList.isEmpty();
    }

    public UserProfileDto getUserByLogin(String login){
        List<UserCreds> credsList = userCredsDao.getByLogin(login);
        return (UserProfileDto) mapper.convert(checkSingleListSize(credsList).getUser(),
                                               UserProfileDto.class, null);
    }

    public IDto tryAuthorisation(String username, String password, Class convertToClazz) {
        List<UserCreds> credsList = userCredsDao.getByLogin(username);
        UserCreds userCreds = checkSingleListSize(credsList);
        if (userCreds != null && passwordEncoder.matches(password, userCreds.getPassword())) {
            return convertToDto(userCreds, convertToClazz);
        } else {
            return null;
        }
    }

    public IDto getCredsByLogin(String username, Class convertToClazz) {
        List<UserCreds> credsList = userCredsDao.getByLogin(username);
        UserCreds userCreds = checkSingleListSize(credsList);
        return convertToDto(userCreds, convertToClazz);
    }

    @Override
    public boolean delete(IDto dto) {
        tryCastPossibilities(dto, UserInfoDto.class);
        List<UserCreds> credsList = userCredsDao.readByPk(dto.getId());
        userCredsDao.delete(checkSingleListSize(credsList));
        return true;
    }
}
