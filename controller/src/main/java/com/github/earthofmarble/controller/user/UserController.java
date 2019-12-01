package com.github.earthofmarble.controller.user;

import com.github.earthofmarble.controller.AbstractController;
import com.github.earthofmarble.model.dto.user.user.UserExtendedDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.CommonFilter;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.api.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping //not tested
    public List<UserInfoDto> getUsers(@RequestParam(value = "first", required = false) Integer firstElement,
                                      @RequestParam(value = "size", required = false) Integer pageSize) {
        IFilter filter = fillFilterPagination(null, firstElement, pageSize);
        return castModelDtoList(userService.readWithFilter(filter, UserInfoDto.class), UserInfoDto.class);
    }

    @GetMapping(value = "/{userId}") //tested
    public UserExtendedDto getUser(@PathVariable(value = "userId") int userId) {
        return (UserExtendedDto) userService.readById(userId, UserExtendedDto.class);
    }



}
