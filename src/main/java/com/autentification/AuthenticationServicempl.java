package com.autentification;


import com.Entity.User;
import com.Entity.UsersToken;
import com.dto.LoginRqDto;
import com.dto.LoginRsDto;
import com.dto.RegistrationRqDto;
import com.dto.RegistrationRsDto;
import com.repository.UserRepository;
import com.repository.UsersTokenRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Date;

@Singleton
public class AuthenticationServicempl implements AuthenticationService {
    @Inject
    UserRepository userRepository;
    @Inject
    UsersTokenRepository usersTokenRepository;

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
                    usersTokenRepository.getUsersTokenList().add(new UsersToken(rqLogin,"Bearer "+token));
                }
            }
        }
        LoginRsDto loginRsDto = new LoginRsDto();
        loginRsDto.setVerification(ver);
        loginRsDto.setToken(token);

        return loginRsDto;
    }

    @Override
    public RegistrationRsDto Registration(RegistrationRqDto registrationRqDto) {
        boolean ver = true;
        Map<String, Object> tokenData = new HashMap<>();
        String token = "";

        String rqLogin = registrationRqDto.getLogin().toLowerCase(Locale.ROOT);
        String rqEmail = registrationRqDto.getEmail().toLowerCase(Locale.ROOT);

        for (User ur : userRepository.getUserList()) {
            String userLogin = ur.getLogin().toLowerCase(Locale.ROOT);
            String userEmail = ur.getEmail().toLowerCase(Locale.ROOT);

            if (rqLogin.equals(userLogin) || rqEmail.equals(userEmail)) {
                RegistrationRsDto registrationRsDto = new RegistrationRsDto();
                ver = false;
                registrationRsDto.setVerification(ver);
                registrationRsDto.setToken(token);
                return registrationRsDto;
            }
        }

        userRepository.getUserList().add(new User(userRepository.getUserList().size()+1,registrationRqDto.getSurname(),
                registrationRqDto.getName(), registrationRqDto.getMiddleName(), registrationRqDto.getAge(), registrationRqDto.getEmail(),
                registrationRqDto.getLogin().toLowerCase(Locale.ROOT), registrationRqDto.getPassword().toLowerCase(Locale.ROOT)));

        tokenData.put("clientType", "user");
        tokenData.put("userID", userRepository.getUserList().size()+1);
        tokenData.put("username", registrationRqDto.getSurname() + " " +registrationRqDto.getName() + " " + registrationRqDto.getMiddleName());
        tokenData.put("token_create_date", new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        tokenData.put("token_expiration_date", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);
        String key = "abc123";
        token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        usersTokenRepository.getUsersTokenList().add(new UsersToken(rqLogin,"Bearer "+token));

        RegistrationRsDto registrationRsDto = new RegistrationRsDto();
        registrationRsDto.setVerification(ver);
        registrationRsDto.setToken(token);
        return registrationRsDto;
    }

    @Override
    public String DecodeTokenDate(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        String[] payloadData = payload.split(",");
        String[] date = payloadData[1].split(":");
        return date[1];
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

