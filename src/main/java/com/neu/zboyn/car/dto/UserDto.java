package com.neu.zboyn.car.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String nickname;
    private String phoneNumber;
    private Integer deptId;
    private Integer status; // 0: 禁用, 1: 启用
    private String remark;
}
