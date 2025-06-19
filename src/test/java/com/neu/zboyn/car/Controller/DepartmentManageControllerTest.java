package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.DepartmentManageController;
import com.neu.zboyn.car.controller.UserManageController;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Department;
import com.neu.zboyn.car.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentManageController.class)
public class DepartmentManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testGetDepartments() throws Exception {
        List<Department> departments = Arrays.asList(new Department());
        Mockito.when(departmentService.getDepartmentinfo(null, null)).thenReturn(Response.success(departments));

        mockMvc.perform(get("/api/system/dept/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = new Department();
        Mockito.when(departmentService.insertDepartment(department)).thenReturn(Response.success(null));

        mockMvc.perform(post("/api/system/dept")
                        .contentType("application/json")
                        .content("{\"deptId\": 1, \"parentId\": 0, \"deptName\": \"Test Dept\"}"))
                .andExpect(status().isOk());
    }
}