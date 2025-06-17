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

    /**
     *  根据token得到用户信息
     * @param token Token
     * @return 用户信息
     */
    Response<User> getUserInfoFromToken(String token);

     Response<Void> register(String username,
                                   String nickname,
                                   String password,
                                   int deptId,
                                   String phoneNumber);

}
