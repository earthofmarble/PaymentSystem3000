package com.github.earthofmarble.service.api.user;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.dto.user.user.UserProfileDto;
import com.github.earthofmarble.model.model.user.UserCreds;
import com.github.earthofmarble.service.api.IGenericService;

/**
 * Created by earthofmarble on Oct, 2019
 */

public interface IUserCredsService extends IGenericService<UserCreds, Integer> {

    boolean tryUsername(String login);
    UserProfileDto getUserByLogin(String login);
    IDto tryAuthorisation(String username, String password, Class convertToClazz);
    IDto getCredsByLogin(String username, Class convertToClazz);

}
