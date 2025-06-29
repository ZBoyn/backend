package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.RoleManageController;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.service.RoleService;
import com.neu.zboyn.car.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RoleManageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        // 创建RoleManageController实例
        RoleManageController roleManageController = new RoleManageController();
        
        // 使用反射设置私有字段
        Field roleServiceField = RoleManageController.class.getDeclaredField("roleService");
        roleServiceField.setAccessible(true);
        roleServiceField.set(roleManageController, roleService);
        
        Field userServiceField = RoleManageController.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(roleManageController, userService);
        
        mockMvc = MockMvcBuilders.standaloneSetup(roleManageController)
                .build();
    }

    @Test
    void testGetRoleList() throws Exception {
        // 准备测试数据
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId("1"); // 确保设置为字符串类型
        roleDto.setRoleName("管理员");
        List<RoleDto> roleList = Collections.singletonList(roleDto);
        PageResult<RoleDto> pageResult = new PageResult<>(roleList, 1, 1, 10);
        Response<PageResult<RoleDto>> expectedResponse = Response.success(pageResult);

        // 模拟服务层方法调用
        when(roleService.getRoleList(1, 10, null, null, null, null, null, null))
                .thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(get("/api/system/role/list")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andDo(print()) // 打印响应内容，用于调试
                .andExpect(jsonPath("$.data.items[0].roleId", is("1"))) // 预期字符串值
                .andExpect(jsonPath("$.data.items[0].roleName", is("管理员")));

        // 验证服务方法被调用
        verify(roleService, times(1)).getRoleList(1, 10, null, null, null, null, null, null);
    }

    @Test
    void testGetRoleById() throws Exception {
        // 准备测试数据
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(String.valueOf(1L));
        roleDto.setRoleName("管理员");
        Response<RoleDto> expectedResponse = Response.success(roleDto);

        // 模拟服务层方法调用
        when(roleService.getRoleById(1L)).thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(get("/api/system/role/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.roleId", is("1")))
                .andExpect(jsonPath("$.data.roleName", is("管理员")));

        // 验证服务方法被调用
        verify(roleService, times(1)).getRoleById(1L);
    }

    @Test
    void testCreateRole() throws Exception {
        // 准备测试数据
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("管理员");
        Response<Void> expectedResponse = Response.success(null); // 修正：正确调用静态方法

        // 模拟服务层方法调用
        when(roleService.createRole(roleDto)).thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(post("/api/system/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"管理员\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));

        // 验证服务方法被调用
        verify(roleService, times(1)).createRole(roleDto);
    }

    @Test
    void testUpdateRole() throws Exception {
        // 准备测试数据
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(String.valueOf(1L));
        roleDto.setRoleName("管理员更新");
        Response<Void> expectedResponse = Response.success(null);

        // 模拟服务层方法调用
        when(roleService.updateRole("1", roleDto)).thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(put("/api/system/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleId\":1,\"roleName\":\"管理员更新\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));

        // 验证服务方法被调用
        verify(roleService, times(1)).updateRole("1", roleDto);
    }

    @Test
    void testDeleteRole() throws Exception {
        // 准备测试数据
        Response<Void> expectedResponse = Response.success(null);

        // 模拟服务层方法调用
        when(roleService.deleteRole(1L)).thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(delete("/api/system/role/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));

        // 验证服务方法被调用
        verify(roleService, times(1)).deleteRole(1L);
    }

    @Test
    void testChangeUser() throws Exception {
        // 准备测试数据
        Response<Void> expectedResponse = Response.success(null);

        // 模拟服务层方法调用
        when(roleService.changeRoleUser("1", "2")).thenReturn(expectedResponse);

        // 执行请求
        mockMvc.perform(put("/api/system/role/2/change-user")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)));

        // 验证服务方法被调用
        verify(roleService, times(1)).changeRoleUser("1", "2");
    }
}