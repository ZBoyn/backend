package com.neu.zboyn.car.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String username;
    private String nickname;
    private String password;
    private int deptId;
    private String phoneNumber;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private String accessToken;
    private List<String> roles; // 用户角色列表
}
