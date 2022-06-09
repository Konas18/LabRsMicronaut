package com.autentification;

import com.dto.LoginRqDto;
import com.dto.LoginRsDto;

public interface AuthenticationService {
    LoginRsDto Verification(LoginRqDto loginRqDto);
}
