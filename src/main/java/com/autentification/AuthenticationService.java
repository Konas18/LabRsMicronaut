package com.autentification;

import com.dto.LoginRqDto;
import com.dto.LoginRsDto;
import com.dto.RegistrationRqDto;
import com.dto.RegistrationRsDto;

public interface AuthenticationService {
    LoginRsDto Verification(LoginRqDto loginRqDto);

    RegistrationRsDto Registration(RegistrationRqDto registrationRqDto);
}
