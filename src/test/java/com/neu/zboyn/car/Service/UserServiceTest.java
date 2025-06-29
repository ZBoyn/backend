package com.neu.zboyn.car.Service;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.mapper.UserManageMapper;
import com.neu.zboyn.car.model.Sysconfig;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.SysConfigService;
import com.neu.zboyn.car.service.impl.UserServiceImpl;
import com.neu.zboyn.car.util.BCryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserManageMapper userManageMapper;

    @Mock
    private BCryptUtil bCryptUtil;

    @Mock
    private SysConfigService sysConfigService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUsername("testuser");
        testUser.setNickname("测试用户");
        testUser.setPhoneNumber("13800138000");
        testUser.setStatus(1);

        testUserDto = new UserDto();
        testUserDto.setUsername("testuser");
        testUserDto.setNickname("测试用户");
        testUserDto.setPhoneNumber("13800138000");
        testUserDto.setStatus(1);
    }

    @Test
    void getUserList_Success() {
        // 准备测试数据
        List<User> userList = Arrays.asList(testUser);
        when(userManageMapper.selectUserList(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(userList);
        when(userManageMapper.selectUserCount(anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<User>> response = userService.getUserList(1, 10, null, null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("获取成功", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getItems().size());
        assertEquals(1, response.getData().getTotal());
        assertEquals(1, response.getData().getPage());
        assertEquals(10, response.getData().getPageSize());

        // 验证方法调用
        verify(userManageMapper).selectUserList(0, 10, null, null, null, null, null, null, null, null);
        verify(userManageMapper).selectUserCount(null, null, null, null, null, null, null, null);
    }

    @Test
    void getUserList_WithFilters() {
        // 准备测试数据
        List<User> userList = Arrays.asList(testUser);
        when(userManageMapper.selectUserList(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(userList);
        when(userManageMapper.selectUserCount(anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<User>> response = userService.getUserList(1, 10, "1", "test", "测试", 1L, "138", 1, "2023-01-01", "2023-12-31");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());

        // 验证方法调用
        verify(userManageMapper).selectUserList(0, 10, "1", "test", "测试", 1L, "138", 1, "2023-01-01", "2023-12-31");
        verify(userManageMapper).selectUserCount("1", "test", "测试", 1L, "138", 1, "2023-01-01", "2023-12-31");
    }

    @Test
    void getUserList_EmptyResult() {
        // 准备测试数据
        when(userManageMapper.selectUserList(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(Collections.emptyList());
        when(userManageMapper.selectUserCount(anyString(), anyString(), anyString(), anyLong(), anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试
        Response<PageResult<User>> response = userService.getUserList(1, 10, null, null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        assertEquals(0, response.getData().getTotal());
    }

    @Test
    void createUser_Success() {
        // 准备测试数据
        doNothing().when(userManageMapper).create(any(UserDto.class));

        // 执行测试
        Response<Void> response = userService.createUser(testUserDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("用户创建成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userManageMapper).create(testUserDto);
    }

    @Test
    void updateUser_Success() {
        // 准备测试数据
        doNothing().when(userManageMapper).update(anyString(), any(UserDto.class));

        // 执行测试
        Response<Void> response = userService.updateUser("1", testUserDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("<UNK>", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userManageMapper).update("1", testUserDto);
    }

    @Test
    void deleteUser_Success() {
        // 准备测试数据
        doNothing().when(userManageMapper).delete(anyString());

        // 执行测试
        Response<Void> response = userService.deleteUser("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("<UNK>", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userManageMapper).delete("1");
    }

    @Test
    void resetPassword_Success_WithDefaultPassword() {
        // 准备测试数据
        when(sysConfigService.getByConfigKey("sys.user.initPassword")).thenReturn(null);
        when(bCryptUtil.hashPassword("123456")).thenReturn("hashedPassword");
        doNothing().when(userManageMapper).resetPassword(anyString(), anyString());

        // 执行测试
        Response<Void> response = userService.resetPassword("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("密码重置成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(sysConfigService).getByConfigKey("sys.user.initPassword");
        verify(bCryptUtil).hashPassword("123456");
        verify(userManageMapper).resetPassword("1", "hashedPassword");
    }

    @Test
    void resetPassword_Success_WithCustomPassword() {
        // 准备测试数据
        Sysconfig config = new Sysconfig();
        config.setConfigValue("customPassword");
        when(sysConfigService.getByConfigKey("sys.user.initPassword")).thenReturn(config);
        when(bCryptUtil.hashPassword("customPassword")).thenReturn("hashedPassword");
        doNothing().when(userManageMapper).resetPassword(anyString(), anyString());

        // 执行测试
        Response<Void> response = userService.resetPassword("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("密码重置成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(sysConfigService).getByConfigKey("sys.user.initPassword");
        verify(bCryptUtil).hashPassword("customPassword");
        verify(userManageMapper).resetPassword("1", "hashedPassword");
    }

    @Test
    void resetPassword_Success_WithEmptyConfigValue() {
        // 准备测试数据
        Sysconfig config = new Sysconfig();
        config.setConfigValue("");
        when(sysConfigService.getByConfigKey("sys.user.initPassword")).thenReturn(config);
        when(bCryptUtil.hashPassword("123456")).thenReturn("hashedPassword");
        doNothing().when(userManageMapper).resetPassword(anyString(), anyString());

        // 执行测试
        Response<Void> response = userService.resetPassword("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("密码重置成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(sysConfigService).getByConfigKey("sys.user.initPassword");
        verify(bCryptUtil).hashPassword("123456");
        verify(userManageMapper).resetPassword("1", "hashedPassword");
    }

    @Test
    void changeUserRole_Success() {
        // 准备测试数据
        doNothing().when(userManageMapper).changeUserRole(anyString(), anyString());

        // 执行测试
        Response<Void> response = userService.changeUserRole("1", "2");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("用户角色变更成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userManageMapper).changeUserRole("1", "2");
    }

    @Test
    void getUserByCreatorId_Success() {
        // 准备测试数据
        when(userManageMapper.findByCreatorId("1")).thenReturn(testUser);

        // 执行测试
        Response<User> response = userService.getUserByCreatorId("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(testUser.getUserId(), response.getData().getUserId());
        assertEquals(testUser.getUsername(), response.getData().getUsername());

        // 验证方法调用
        verify(userManageMapper).findByCreatorId("1");
    }

    @Test
    void getUserByCreatorId_NotFound() {
        // 准备测试数据
        when(userManageMapper.findByCreatorId("999")).thenReturn(null);

        // 执行测试
        Response<User> response = userService.getUserByCreatorId("999");

        // 验证结果
        assertNotNull(response);
        assertEquals(404, response.getCode());
        assertEquals("用户不存在", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(userManageMapper).findByCreatorId("999");
    }
} 