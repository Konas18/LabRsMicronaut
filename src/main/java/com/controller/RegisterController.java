package com.controller;

import com.Entity.User;
import com.Entity.UsersToken;
import com.autentification.AuthenticationServicempl;
import com.dto.LoginRqDto;
import com.dto.LoginRsDto;
import com.dto.RegistrationRqDto;
import com.dto.RegistrationRsDto;
import com.service.UserService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;


@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
public class RegisterController {

    @Inject
    UserService userService;
    @Inject
    private AuthenticationServicempl authenticationService;

    @Post(value = "/user/login", consumes = MediaType.APPLICATION_JSON)
    public LoginRsDto Verification(@Body LoginRqDto loginRqDto){
        return authenticationService.Verification(loginRqDto);
    }
    @Post(value = "/user/registration", consumes = MediaType.APPLICATION_JSON)
    public RegistrationRsDto Registration(@Body RegistrationRqDto registrationRqDto){
        return authenticationService.Registration(registrationRqDto);
    }
}
