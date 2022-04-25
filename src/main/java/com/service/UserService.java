package com.service;

import com.dto.*;

import java.util.List;

public interface UserService {
    List<User> getList();
    GetUserByIdRsDto getById(int id);
    LoginRsDto Verification(LoginRqDto loginRqDto);

    RegistrationRsDto Registration(RegistrationRqDto registrationRqDto);
}
