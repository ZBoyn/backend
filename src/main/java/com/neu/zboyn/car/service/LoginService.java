package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.LoginToken;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.User;

public interface LoginService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录响应结果
     */
    Response<LoginToken> login(String username, String password);
    Response<User> getUserInfoFromToken(String token);
}
