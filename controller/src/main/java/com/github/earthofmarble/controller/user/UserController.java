package com.github.earthofmarble.controller.user;

import com.github.earthofmarble.controller.AbstractController;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.user.user.UserExtendedDto;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.api.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by earthofmarble on Nov, 2019
 */
@RestController
@RequestMapping(value = "/user/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController extends AbstractController {

    private IUserService userService;
    private IUserCredsService credsService;

    @Autowired
    public UserController(IUserService userService, IUserCredsService userCredsService) {
      this.userService = userService;
      this.credsService = userCredsService;
    }

    @GetMapping(value = "/{userId}") //not tested
    public UserExtendedDto getAccount(@PathVariable(value = "userId") int userId) {
        return (UserExtendedDto) userService.readById(userId, UserExtendedDto.class);
    }

}
