package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.LoginController;
import com.neu.zboyn.car.dto.LoginRequest;
import com.neu.zboyn.car.dto.LoginToken;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private LoginRequest loginRequest;
    private LoginToken loginToken;
    private User user;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("password");

        loginToken = new LoginToken();
        loginToken.setAccessToken("test-token");

        user = new User();
        user.setUserId(1);
        user.setUsername("admin");

    }

    @Test
    void login_Success() throws Exception {
        // 模拟服务返回
        Response<LoginToken> response = Response.success(loginToken);
        when(loginService.login(anyString(), anyString())).thenReturn(response);

        // 执行请求
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.accessToken").value("test-token"));
    }

    @Test
    void login_InvalidCredentials() throws Exception {
        // 模拟服务返回错误
        Response<LoginToken> response = Response.error(401, "Invalid credentials", "error");
        when(loginService.login(anyString(), anyString())).thenReturn(response);

        // 执行请求
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"wrong\",\"password\":\"credentials\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void getUserInfo_Success() throws Exception {
        // 模拟服务返回
        Response<User> response = Response.success(user);
        when(loginService.getUserInfoFromToken(anyString())).thenReturn(response);

        // 执行请求
        mockMvc.perform(get("/api/user/info")
                        .header("Authorization", "test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    void getUserInfo_MissingToken() throws Exception {
        // 执行请求（不带Authorization头）
        mockMvc.perform(get("/api/user/info"))
                .andExpect(status().isBadRequest());
    }

}