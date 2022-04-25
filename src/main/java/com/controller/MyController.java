package com.controller;

import com.dto.*;
import com.service.UserService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;

@Controller
public class MyController {
    @Inject
    UserService userService;

    @Get( "/user/list")
    public List<User> getUserList(){
        return userService.getList();
    }

    @Get(value = "/user/find", consumes = MediaType.APPLICATION_JSON)
    public GetUserByIdRsDto getById(@RequestAttribute GetUserByIdRqDto getUserByIdRqDto){
        System.out.println(getUserByIdRqDto.getId());
        return userService.getById(getUserByIdRqDto.getId());
    }

    @Post(value = "/user/login", consumes = MediaType.APPLICATION_JSON)
    public LoginRsDto Verification(@RequestAttribute LoginRqDto loginRqDto){
        return userService.Verification(loginRqDto);
    }

    @Post(value = "/user/registration", consumes = MediaType.APPLICATION_JSON)
    public RegistrationRsDto Registration(@RequestAttribute RegistrationRqDto registrationRqDto){
        return userService.Registration(registrationRqDto);
    }
}
