package com.autentification;


import com.Entity.User;
import com.dto.LoginRqDto;
import com.dto.LoginRsDto;
import com.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Singleton
public class AuthenticationServicempl implements AuthenticationService {
    @Inject
    UserRepository userRepository;
    @Inject
    public void main() {
        userRepository.getUserList().add(new User(1,"Заболотский Александр Николаевич", 19, "Zab1@mail.ru", "zab1", "123"));
        userRepository.getUserList().add(new User(2,"Заболотская Светлана Егоровна", 45, "Zab2@mail.ru", "zab2", "123"));
        userRepository.getUserList().add(new User(3,"Заболотский Николай Иванович", 50, "Zab3@mail.ru", "zab3", "123"));
    }

    @Override
    public LoginRsDto Verification(LoginRqDto loginRqDto) {

        Map<String, Object> tokenData = new HashMap<>();

        boolean ver = false;
        String token = "";
        String rqLogin = loginRqDto.getLogin();
        String rqPassword = loginRqDto.getPassword();

        for (User ur : userRepository.getUserList())
        {
            String userLogin =  ur.getLogin();
            String userPassword =  ur.getPassword();

            if (rqLogin.equals(userLogin)){
                if (rqPassword.equals(userPassword) ){
                    ver = true;

                    tokenData.put("clientType", "user");
                    tokenData.put("userID", ur.getId());
                    tokenData.put("username", ur.getName());
                    tokenData.put("token_create_date", new Date().getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, 100);
                    tokenData.put("token_expiration_date", calendar.getTime());
                    JwtBuilder jwtBuilder = Jwts.builder();
                    jwtBuilder.setExpiration(calendar.getTime());
                    jwtBuilder.setClaims(tokenData);
                    String key = "abc123";
                    token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
                }
            }
        }
        LoginRsDto loginRsDto = new LoginRsDto();
        loginRsDto.setVerification(ver);
        loginRsDto.setToken(token);
        return loginRsDto;
    }

    public boolean passwordMath(Integer id, String password) {
        Optional<User> candidate = Optional.ofNullable(userRepository.getUserList().get(id));
        if (candidate.isEmpty()) {
            return false;
        }
        User user = candidate.get();
        byte[] salt = decodeB64(user.getPassword());
        byte[] hashedPassword = decodeB64(user.getPassword());
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        md.update(salt);
        byte[] toCheck = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Arrays.equals(toCheck, hashedPassword);
    }

    private String encodeB64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decodeB64(String data) {
        return Base64.getDecoder().decode(data);
    }
}

