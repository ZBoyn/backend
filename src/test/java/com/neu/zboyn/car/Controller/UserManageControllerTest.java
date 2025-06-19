package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.UserManageController;
import com.neu.zboyn.car.service.DepartmentService;
import com.neu.zboyn.car.service.RoleService;
import com.neu.zboyn.car.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; // 导入 @MockBean
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserManageController.class)
public class UserManageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // 使用 @MockBean 来创建和注入模拟的 Service
    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void testGetUserList() throws Exception {
        // 在这里，你可以定义当调用 userService 的方法时，应该返回什么
        // 例如: when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        // 如果不定义，默认会返回null或空集合，但不会抛出异常

        mockMvc.perform(get("/api/system/user/list"))
                .andExpect(status().isOk());
    }
}