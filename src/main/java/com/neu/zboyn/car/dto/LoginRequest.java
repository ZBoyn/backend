package com.neu.zboyn.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty
    private String selectAccount;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private Boolean captcha;

    public LoginRequest(String selectAccount, String username, String password, Boolean captcha) {
    }

    public LoginRequest() {

    }
}
