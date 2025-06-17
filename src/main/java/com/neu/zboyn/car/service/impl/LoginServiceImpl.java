package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.UserMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.LoginService;
import com.neu.zboyn.car.util.BCryptUtil;
import com.neu.zboyn.car.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptUtil bCryptUtil;

    @Override
    public Response<User> login(String username, String password) {
        User user = userMapper.Login(username);

        if (user == null) {
            System.out.println("登录失败：用户名不存在 - " + username);
            return new Response<>(-1, null, "用户名或密码错误", "用户名或密码错误");
        }

        // 验证密码
        if (!bCryptUtil.checkPassword(password, user.getPassword())) {
            System.out.println("登录失败：密码错误 - " + username);
            return new Response<>(-1, null, "用户名或密码错误", "用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            System.out.println("登录失败：账号已被禁用");
            return new Response<>(-1, null, "您的账号已被禁用，请联系管理员", "error");
        }

        String accessToken = JWTUtil.generateToken(user.getUserId(), user.getUsername(), user.getUsername());

        if (accessToken == null) {
            System.out.println("登录失败：生成Token时发生错误 - " + username);
            return new Response<>(-1, null, "服务器内部错误，请稍后再试", "error");
        }

        // 设置token到用户对象中
        user.setToken(accessToken);
        
        return new Response<>(0, user, "登录成功", "success");
    }
} 