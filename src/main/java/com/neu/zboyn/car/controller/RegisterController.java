package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.RegisterRequest;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.service.LoginService;
import com.neu.zboyn.car.util.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册模块
 */
@RestController
@RequestMapping("/api/auth")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private BCryptUtil bcryptUtil;

    /**
     * 用户注册接口
     * @param registerRequest 注册请求对象，包含用户名、昵称和密码等信息
     * @return Response<Void> 返回注册结果
     */
    @RequestMapping("/register")
    public Response<Void> register(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String nickname = registerRequest.getNickname();
        String password = registerRequest.getPassword();
        // 密码加密存储数据库
        String hashPassword = bcryptUtil.hashPassword(password);
        int deptId = registerRequest.getDeptId();
        String phoneNumber = registerRequest.getPhoneNumber();
        // 调用登录服务的注册方法
        return loginService.register(username, nickname, hashPassword, deptId, phoneNumber);
    }
}
