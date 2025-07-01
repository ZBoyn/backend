package com.neu.zboyn.car.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neu.zboyn.car.dto.LoginToken;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.RoleMapper;
import com.neu.zboyn.car.mapper.UserMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.LoginService;
import com.neu.zboyn.car.util.BCryptUtil;
import com.neu.zboyn.car.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptUtil bCryptUtil;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Response<LoginToken> login(String username, String password) {
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

        if (user.getStatus().equals("0")) {
            System.out.println("登录失败：账号已被禁用");
            return new Response<>(-1, null, "您的账号已被禁用，请联系管理员", "您的账号已被禁用，请联系管理员");
        }

        String accessToken = JWTUtil.generateToken(user.getUserId(), user.getUsername(), user.getUsername());

        if (accessToken == null) {
            System.out.println("登录失败：生成Token时发生错误 - " + username);
            return new Response<>(-1, null, "服务器内部错误，请稍后再试", "服务器内部错误，请稍后再试");
        }

        // 查询角色名并设置到user
        List<String> roles = roleMapper.getRoleNamesByUserId(user.getUserId());
        user.setRoles(roles);
        user.setAccessToken(accessToken);

        LoginToken loginToken = new LoginToken(accessToken);

        return new Response<>(0, loginToken, "登录成功", "success");
    }

    @Override
    public Response<User> getUserInfoFromToken(String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
                System.out.println("已经取出token");
            }

            // 验证并解析token
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            // 从token中获取用户信息
            // 仅获取 id 信息剩下从数据库中取出
            Integer userId = jwt.getClaim("id").asInt();
            User userByid = userMapper.findByid(userId);
            // System.out.println(userByid);

            if (userByid == null){
                System.out.println("用户不存在");
                return new Response<>(-1, null, "用户不存在", "用户不存在");
            }

            List<String> roles = roleMapper.getRoleNamesByUserId(userId);
            userByid.setRoles(roles);

            return new Response<>(0, userByid, "获取用户信息成功", "success");

        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(-1, null, "token无效或服务器异常", "error");
        }
    }

    @Override
    @Transactional
    public Response<Void> register(String username,
                                   String nickname,
                                   String password,
                                   int deptId,
                                   String phoneNumber) {
        // 先插用户表
        userMapper.insertUser(username, nickname, password, deptId, phoneNumber);
        // 获取最新插入的用户ID
        User user = userMapper.Login(username);
        if (user == null) {
            System.out.println("注册失败：用户插入失败 - " + username);
            return new Response<>(-1, null, "注册失败，请稍后再试", "注册失败，请稍后再试");
        }
        // 插入用户角色表
        Integer latestUserId = user.getUserId();
        userMapper.insertUserRole(latestUserId);
        System.out.println("注册成功，用户ID：" + latestUserId);
        return new Response<>(0, null, "注册成功", "success");
    }
}