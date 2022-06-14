package com.dto;

public class RegistrationRsDto {
    private boolean verification;
    private String token;

    public boolean isVerification() {
        return verification;
    }
    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
