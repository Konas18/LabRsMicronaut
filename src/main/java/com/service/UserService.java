package com.service;

import com.Entity.User;
import com.dto.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface UserService {
    List<User> getList();

    String getFriends() throws IOException;

    GetUserByIdRsDto getById(int id);
    LoginRsDto Verification(LoginRqDto loginRqDto);
    RegistrationRsDto Registration(RegistrationRqDto registrationRqDto);
}
