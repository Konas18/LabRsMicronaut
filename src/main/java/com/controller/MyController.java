package com.controller;

import com.Entity.User;
import com.autentification.AuthenticationService;
import com.autentification.AuthenticationServicempl;
import com.dto.*;
import com.service.UserService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.List;
@PermitAll
@Controller
public class MyController {
    @Inject
    UserService userService;
    @Inject
    AuthenticationService authenticationService;

    @Get( "/user/list")
    public List<User> getUserList(){
        return userService.getList();
    }

    @Post(value = "/user/find", consumes = MediaType.APPLICATION_JSON)
    public GetUserByIdRsDto getById(@Body GetUserByIdRqDto getUserByIdRqDto){
        return userService.getById(getUserByIdRqDto.getId());
    }

    @Post(value = "/user/login", consumes = MediaType.APPLICATION_JSON)
    public LoginRsDto Verification(@Body LoginRqDto loginRqDto){
        return authenticationService.Verification(loginRqDto);
    }

    @Get( "/user/friends")
    public String getFriends() throws IOException {
        return userService.getFriends();
    }

    @Post(value = "/user/registration", consumes = MediaType.APPLICATION_JSON)
    public RegistrationRsDto Registration(@Body RegistrationRqDto registrationRqDto){
        return userService.Registration(registrationRqDto);
    }
}
