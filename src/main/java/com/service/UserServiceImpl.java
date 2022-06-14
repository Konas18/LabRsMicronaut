package com.service;

import com.Entity.User;
import com.dto.*;
import com.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.http.client.methods.HttpGet;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    public void main() {
        userRepository.getUserList().add(new User(1,"Заболотский", "Александр", "Николаевич", 19, "Zab1@mail.ru", "zab1", "123"));
        userRepository.getUserList().add(new User(2,"Заболотская", "Светлана", "Егоровна", 45, "Zab2@mail.ru", "zab2", "123"));
        userRepository.getUserList().add(new User(3,"Заболотский", "Николай", "Иванович", 50, "Zab3@mail.ru", "zab3", "123"));
    }

    @Override
    public String getFriends() throws IOException {

        HttpGet token = new HttpGet("https://oauth.vk.com/authorize?client_id=8164338&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.131&state=123456");

        String acessToken = "e8e6090aa9c9b10aa6d79c55ec21c8caff0de10b144c688d836693e0dbd7d82f694910ee1980f7bf88093";

        String url = "https://api.vk.com/method/friends.get?v=5.131&count=5&access_token="+acessToken+"&&fields=name";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return response.toString();
    }

    @Override
    public GetUserByIdRsDto getById(int id) {
        for (Integer ur=0; ur<userRepository.getUserList().size(); ur++){
            if (id == ur+1){
                GetUserByIdRsDto user = new GetUserByIdRsDto();
                user.setUser(userRepository.getUserList().get(ur));
                return user;
                //return GetUserByIdRsDto.builder().user(userRepository.getUserList().get(ur)).build();
            }
        }
        return null;
    }

    @Override
    public List<User> getList() {
        return userRepository.getUserList();
    }
}
