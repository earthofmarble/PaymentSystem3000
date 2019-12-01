package com.github.earthofmarble.controller.user;

import com.github.earthofmarble.controller.AbstractController;
import com.github.earthofmarble.model.dto.user.user.UserExtendedDto;
import com.github.earthofmarble.model.dto.user.user.UserInfoDto;
import com.github.earthofmarble.model.dto.user.user.UserProfileDto;
import com.github.earthofmarble.model.dto.user.usercreds.UserCredsDto;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.service.api.user.IUserCredsService;
import com.github.earthofmarble.service.api.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by earthofmarble on Nov, 2019
 */
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class AuthController extends AbstractController {
    private final IUserCredsService credsService;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(IUserCredsService credsService, IUserService userService,
                          AuthenticationManager authenticationManager) {
        this.credsService = credsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @GetMapping(value = "sign-in")//not tested
    public boolean logIn(@RequestBody UserCredsDto userCreds,
                         HttpServletRequest request, HttpServletResponse response) {
        UserCredsDto userCredsDto = (UserCredsDto) credsService.tryAuthorisation(userCreds.getUsername(),
                userCreds.getPassword(),
                UserCredsDto.class);
        if (userCredsDto != null && userCredsDto.getId() != null) {
            authenticateUserAndSetSession(userCreds.getUsername(), userCreds.getPassword(), request);
            sendRedirect(response, "user/" + userCredsDto.getId());
        }
        return false;
    }

    @GetMapping(value = "log-out")//not tested
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate();
            return "true";
        }
        return "false";
    }

    @PostMapping("sign-up")//not tested
    public UserProfileDto signUp(@RequestBody UserProfileDto object,
                                 HttpServletRequest request, HttpServletResponse response) {
        boolean create = userService.create(object);
        if (create) {
            authenticateUserAndSetSession(object.getUserCreds().getUsername(),
                    object.getUserCreds().getPassword(), request);
        }
//        sendRedirect(response, "/"); //TODO нужно куда-то редиректить
        return new UserProfileDto();
    }
}