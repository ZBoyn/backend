package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.LoginRequest;
import com.neu.zboyn.car.dto.LoginToken;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 用户登录模块
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 验证登录逻辑
     * @param loginRequest 前端登录接口参数 password, username
     * @return accessToken: string
     */
    @RequestMapping("/auth/login")
    public Response<LoginToken> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        return loginService.login(username, password);
    }

    /**
     * 登录后前端返回Headers
     * @param token Token
     * @return 用户信息
     */
    @RequestMapping("/user/info")
    public Response<User> getUserInfo(@RequestHeader("Authorization") String token){
        return loginService.getUserInfoFromToken(token);
    }

    @RequestMapping("/auth/codes")
    public Response<List<String>> getAuthCodes() {
        List<String> codes = new ArrayList<>();
        return new Response<>(0, codes, null, "ok");
    }


}
