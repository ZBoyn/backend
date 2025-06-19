package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.RegisterController;
import com.neu.zboyn.car.dto.RegisterRequest;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.service.LoginService;
import com.neu.zboyn.car.util.BCryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private BCryptUtil bcryptUtil;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setNickname("Test User");
        registerRequest.setPassword("password123");
        registerRequest.setDeptId(1);
        registerRequest.setPhoneNumber("13800138000");
    }

    @Test
    void register_Success() throws Exception {
        // 模拟BCryptUtil加密
        when(bcryptUtil.hashPassword(anyString())).thenReturn("$2a$10$testHashedPassword");

        // 模拟服务返回成功
        when(loginService.register(anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(Response.success(null));

        // 执行请求并验证完整JSON结构
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"nickname\":\"Test User\",\"password\":\"password123\",\"deptId\":1,\"phoneNumber\":\"13800138000\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("操作成功"))
                .andExpect(jsonPath("$.error").value("ok"));
    }

    @Test
    void register_MissingUsername() throws Exception {
        // 移除用户名
        registerRequest.setUsername(null);

        // 执行请求（假设控制器有参数验证）
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nickname\":\"Test User\",\"password\":\"password123\",\"deptId\":1,\"phoneNumber\":\"13800138000\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }

    @Test
    void register_MissingPassword() throws Exception {
        // 移除密码
        registerRequest.setPassword(null);

        // 执行请求（假设控制器有参数验证）
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"nickname\":\"Test User\",\"deptId\":1,\"phoneNumber\":\"13800138000\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400));

    }

    @Test
    void register_InvalidDeptId() throws Exception {
        // 设置无效部门ID
        registerRequest.setDeptId(-1);

        // 模拟服务返回错误
        when(loginService.register(anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(Response.error(400, "Invalid department ID", "error"));

        // 执行请求并验证完整JSON结构
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"nickname\":\"Test User\",\"password\":\"password123\",\"deptId\":-1,\"phoneNumber\":\"13800138000\"}"))
                .andDo(print())
                .andExpect(status().isOk()) // 假设服务返回200，但包含错误信息
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid department ID"))
                .andExpect(jsonPath("$.error").value("error"));
    }
}