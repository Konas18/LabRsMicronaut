package com.repository;

import com.dto.User;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Singleton
public class UserRepository {

    private List<User> userList;

    public UserRepository(){
        userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }
}

