package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.LoginToken;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.UserMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.impl.LoginServiceImpl;
import com.neu.zboyn.car.util.BCryptUtil;
import com.neu.zboyn.car.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptUtil bCryptUtil;

    @InjectMocks
    private LoginServiceImpl loginService;

    private User testUser;
    private String testUsername;
    private String testPassword;

    @BeforeEach
    void setUp() {
        testUsername = "admin";
        testPassword = "password";
        
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUsername(testUsername);
        testUser.setPassword("hashedPassword");
        testUser.setStatus(1); // 1表示启用状态
    }

    @Test
    void login_Success() {
        // 准备测试数据
        when(userMapper.Login(testUsername)).thenReturn(testUser);
        when(bCryptUtil.checkPassword(testPassword, testUser.getPassword())).thenReturn(true);
        
        try (MockedStatic<JWTUtil> jwtUtilMock = mockStatic(JWTUtil.class)) {
            jwtUtilMock.when(() -> JWTUtil.generateToken(anyInt(), anyString(), anyString()))
                    .thenReturn("test-token");

            // 执行测试
            Response<LoginToken> response = loginService.login(testUsername, testPassword);

            // 验证结果
            assertNotNull(response);
            assertEquals(0, response.getCode());
            assertEquals("登录成功", response.getMessage());
            assertNotNull(response.getData());
            assertEquals("test-token", response.getData().getAccessToken());

            // 验证方法调用
            verify(userMapper).Login(testUsername);
            verify(bCryptUtil).checkPassword(testPassword, testUser.getPassword());
        }
    }

    @Test
    void login_UserNotFound() {
        // 准备测试数据
        when(userMapper.Login(testUsername)).thenReturn(null);

        // 执行测试
        Response<LoginToken> response = loginService.login(testUsername, testPassword);

        // 验证结果
        assertNotNull(response);
        assertEquals(-1, response.getCode());
        assertEquals("用户名或密码错误", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userMapper).Login(testUsername);
        verify(bCryptUtil, never()).checkPassword(anyString(), anyString());
    }

    @Test
    void login_WrongPassword() {
        // 准备测试数据
        when(userMapper.Login(testUsername)).thenReturn(testUser);
        when(bCryptUtil.checkPassword(testPassword, testUser.getPassword())).thenReturn(false);

        // 执行测试
        Response<LoginToken> response = loginService.login(testUsername, testPassword);

        // 验证结果
        assertNotNull(response);
        assertEquals(-1, response.getCode());
        assertEquals("用户名或密码错误", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userMapper).Login(testUsername);
        verify(bCryptUtil).checkPassword(testPassword, testUser.getPassword());
    }

    @Test
    void login_AccountDisabled() {
        // 准备测试数据
        testUser.setStatus(0); // 0表示禁用状态
        when(userMapper.Login(testUsername)).thenReturn(testUser);
        when(bCryptUtil.checkPassword(testPassword, testUser.getPassword())).thenReturn(true);

        // 执行测试
        Response<LoginToken> response = loginService.login(testUsername, testPassword);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("登录成功", response.getMessage());

        // 验证方法调用
        verify(userMapper).Login(testUsername);
        verify(bCryptUtil).checkPassword(testPassword, testUser.getPassword());
    }

    @Test
    void login_TokenGenerationFailed() {
        // 准备测试数据
        when(userMapper.Login(testUsername)).thenReturn(testUser);
        when(bCryptUtil.checkPassword(testPassword, testUser.getPassword())).thenReturn(true);
        
        try (MockedStatic<JWTUtil> jwtUtilMock = mockStatic(JWTUtil.class)) {
            jwtUtilMock.when(() -> JWTUtil.generateToken(anyInt(), anyString(), anyString()))
                    .thenReturn(null);

            // 执行测试
            Response<LoginToken> response = loginService.login(testUsername, testPassword);

            // 验证结果
            assertNotNull(response);
            assertEquals(-1, response.getCode());
            assertEquals("服务器内部错误，请稍后再试", response.getMessage());
            assertNull(response.getData());
        }
    }





    @Test
    void register_Success() {
        // 准备测试数据
        String username = "newuser";
        String nickname = "新用户";
        String password = "password";
        int deptId = 1;
        String phoneNumber = "13800138000";

        doNothing().when(userMapper).insertUser(username, nickname, password, deptId, phoneNumber);
        when(userMapper.Login(username)).thenReturn(testUser);
        doNothing().when(userMapper).insertUserRole(testUser.getUserId());

        // 执行测试
        Response<Void> response = loginService.register(username, nickname, password, deptId, phoneNumber);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("注册成功", response.getMessage());

        // 验证方法调用
        verify(userMapper).insertUser(username, nickname, password, deptId, phoneNumber);
        verify(userMapper).Login(username);
        verify(userMapper).insertUserRole(testUser.getUserId());
    }

    @Test
    void register_UserInsertFailed() {
        // 准备测试数据
        String username = "newuser";
        String nickname = "新用户";
        String password = "password";
        int deptId = 1;
        String phoneNumber = "13800138000";

        doNothing().when(userMapper).insertUser(username, nickname, password, deptId, phoneNumber);
        when(userMapper.Login(username)).thenReturn(null);

        // 执行测试
        Response<Void> response = loginService.register(username, nickname, password, deptId, phoneNumber);

        // 验证结果
        assertNotNull(response);
        assertEquals(-1, response.getCode());
        assertEquals("注册失败，请稍后再试", response.getMessage());

        // 验证方法调用
        verify(userMapper).insertUser(username, nickname, password, deptId, phoneNumber);
        verify(userMapper).Login(username);
        verify(userMapper, never()).insertUserRole(anyInt());
    }
} 