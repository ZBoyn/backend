package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.mapper.RoleMapper;
import com.neu.zboyn.car.model.Role;
import com.neu.zboyn.car.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;
    private RoleDto testRoleDto;
    private ShowRoleDto testShowRoleDto;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setRoleId("1");
        testRole.setRoleName("管理员");
        testRole.setRoleKey("admin");
        testRole.setStatus("1");

        testRoleDto = new RoleDto();
        testRoleDto.setRoleId("1");
        testRoleDto.setRoleName("管理员");
        testRoleDto.setRoleKey("admin");
        testRoleDto.setStatus("1");

        testShowRoleDto = new ShowRoleDto();
        testShowRoleDto.setRoleId(1);
        testShowRoleDto.setRoleName("管理员");
    }


    @Test
    void createRole_Success() {
        // 准备测试数据
        when(roleMapper.insertRole(any(Role.class))).thenReturn(1);

        // 执行测试
        Response<Void> response = roleService.createRole(testRoleDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(roleMapper).insertRole(argThat(role -> {
            assertEquals("1", role.getRoleId());
            assertEquals("管理员", role.getRoleName());
            assertEquals("admin", role.getRoleKey());
            return true;
        }));
    }

    @Test
    void createRole_Failed() {
        // 准备测试数据
        when(roleMapper.insertRole(any(Role.class))).thenReturn(0);

        // 执行测试
        Response<Void> response = roleService.createRole(testRoleDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("新增失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(roleMapper).insertRole(any(Role.class));
    }


    @Test
    void deleteRole_Success() {
        // 准备测试数据
        when(roleMapper.deleteRole(1L)).thenReturn(1);

        // 执行测试
        Response<Void> response = roleService.deleteRole(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(roleMapper).deleteRole(1L);
    }

    @Test
    void deleteRole_Failed() {
        // 准备测试数据
        when(roleMapper.deleteRole(999L)).thenReturn(0);

        // 执行测试
        Response<Void> response = roleService.deleteRole(999L);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("删除失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(roleMapper).deleteRole(999L);
    }


    @Test
    void getRoleName_EmptyResult() {
        // 准备测试数据
        when(roleMapper.getRole()).thenReturn(Collections.emptyList());

        // 执行测试
        Response<List<ShowRoleDto>> response = roleService.getRoleName();

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().size());

        // 验证方法调用
        verify(roleMapper).getRole();
    }

    @Test
    void changeRoleUser_Success() {
        // 准备测试数据
        doNothing().when(roleMapper).changeRoleUser("1", "2");

        // 执行测试
        Response<Void> response = roleService.changeRoleUser("1", "2");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("用户角色变更成功", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(roleMapper).changeRoleUser("1", "2");
    }
} 