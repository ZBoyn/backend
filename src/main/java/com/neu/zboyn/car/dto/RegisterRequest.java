package com.neu.zboyn.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    @JsonProperty
    private String username;

    @JsonSetter
    private String nickname;

    @JsonProperty
    private String password;

    @JsonSetter
    private int deptId;

    @JsonSetter
    private String phoneNumber;

    @JsonSetter
    private Date createTime;

}
