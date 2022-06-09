package com.controller;

import com.Entity.User;
import com.autentification.AuthenticationServicempl;
import com.dto.RegistrationRqDto;
import com.dto.RegistrationRsDto;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;


@Controller
public class RegisterController {

    @Inject
    private AuthenticationServicempl authenticationService;

//    @Post("/register")
//    public RegistrationRsDto register(@Body RegistrationRqDto candidate) {
//        String username = authenticationService.register(candidate);
//        CurrentUserDTO user = new CurrentUserDTO();
//        user.setUsername(username);
//        return user;
//    }
}
