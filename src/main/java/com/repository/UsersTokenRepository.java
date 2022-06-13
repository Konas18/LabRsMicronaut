package com.repository;

import com.Entity.User;
import com.Entity.UsersToken;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class UsersTokenRepository {
    private List<UsersToken> usersTokenList;

    public UsersTokenRepository(){
        usersTokenList = new ArrayList<>();
    }

    public List<UsersToken> getUsersTokenList() {
        return usersTokenList;
    }
}
