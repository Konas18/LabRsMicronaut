package com.dto;

public class LoginRsDto {
    private boolean verification;
    private String token;


    public boolean isVerification() {
        return verification;
    }
    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return this.token;
    }
}