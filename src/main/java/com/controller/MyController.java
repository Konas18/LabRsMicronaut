package com.controller;

import com.Entity.User;
import com.Entity.UsersToken;
import com.autentification.AuthenticationService;
import com.autentification.AuthenticationServicempl;
import com.dto.*;
import com.repository.UsersTokenRepository;
import com.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class MyController {
    @Inject
    UserService userService;
    @Inject
    AuthenticationService authenticationService;
    @Inject
    UsersTokenRepository usersTokenRepository;

    @Get( "/user/list")
    public List<User> getUserList(@Header(AUTHORIZATION)String token){
        for (UsersToken ut : usersTokenRepository.getUsersTokenList()) {
            String userToken = ut.getUserToken();
            Long l = Long.parseLong(authenticationService.DecodeTokenDate(token));
            if (userToken.equals(token) || l > new Date().getTime()) {
                return userService.getList();
            }
        }
        return null;
    }

    @Post(value = "/user/find", consumes = MediaType.APPLICATION_JSON)
    public GetUserByIdRsDto getById(@Header(AUTHORIZATION)String token, @Body GetUserByIdRqDto getUserByIdRqDto) throws SQLException {
        for (UsersToken ut : usersTokenRepository.getUsersTokenList()) {
            String userToken = ut.getUserToken();
            Long l = Long.parseLong(authenticationService.DecodeTokenDate(token));
            if (userToken.equals(token) || l > new Date().getTime()) {
                return userService.getById(getUserByIdRqDto.getId());
            }
        }
        return null;
    }

    @Get( "/user/friends")
    public String getFriends(@Header(AUTHORIZATION)String token) throws IOException {
        for (UsersToken ut : usersTokenRepository.getUsersTokenList()) {
            String userToken = ut.getUserToken();
            Long l = Long.parseLong(authenticationService.DecodeTokenDate(token));
            if (userToken.equals(token) || l > new Date().getTime()) {
                return userService.getFriends();
            }
        }
        return null;
    }
}
