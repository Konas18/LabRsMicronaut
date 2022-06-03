package com.controller;

import com.Entity.User;
import com.dto.*;
import com.service.UserService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.List;
@Controller
public class MyController {
    @Inject
    UserService userService;

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
        return userService.Verification(loginRqDto);
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
