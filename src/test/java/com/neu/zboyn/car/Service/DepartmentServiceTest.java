package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.DepartmentMapper;
import com.neu.zboyn.car.model.Department;
import com.neu.zboyn.car.service.impl.DepartmentServiceImpl;
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
class DepartmentServiceTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setDeptId(1);
        testDepartment.setDeptName("技术部");
        testDepartment.setStatus(1);
    }

    @Test
    void getDepartmentinfo_Success() {
        // 准备测试数据
        List<Department> departmentList = Arrays.asList(testDepartment);
        when(departmentMapper.getDepartment()).thenReturn(departmentList);

        // 执行测试
        Response<List<Department>> response = departmentService.getDepartmentinfo();

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals(1, response.getData().get(0).getDeptId());
        assertEquals("技术部", response.getData().get(0).getDeptName());

        // 验证方法调用
        verify(departmentMapper).getDepartment();
    }

    @Test
    void getDepartmentinfo_EmptyResult() {
        // 准备测试数据
        when(departmentMapper.getDepartment()).thenReturn(Collections.emptyList());

        // 执行测试
        Response<List<Department>> response = departmentService.getDepartmentinfo();

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().size());

        // 验证方法调用
        verify(departmentMapper).getDepartment();
    }

    @Test
    void getDepartmentinfo_WithFilters_Success() {
        // 准备测试数据
        List<Department> departmentList = Arrays.asList(testDepartment);
        when(departmentMapper.getZBYDepartment("技术", 1)).thenReturn(departmentList);

        // 执行测试
        Response<List<Department>> response = departmentService.getDepartmentinfo("技术", 1);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("技术部", response.getData().get(0).getDeptName());

        // 验证方法调用
        verify(departmentMapper).getZBYDepartment("技术", 1);
    }

    @Test
    void getDepartmentinfo_WithFilters_EmptyResult() {
        // 准备测试数据
        when(departmentMapper.getZBYDepartment("不存在的部门", 1)).thenReturn(Collections.emptyList());

        // 执行测试
        Response<List<Department>> response = departmentService.getDepartmentinfo("不存在的部门", 1);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().size());

        // 验证方法调用
        verify(departmentMapper).getZBYDepartment("不存在的部门", 1);
    }

    @Test
    void insertDepartment_Success() {
        // 准备测试数据
        when(departmentMapper.insertDepartment(any(Department.class))).thenReturn(1);

        // 执行测试
        Response<Void> response = departmentService.insertDepartment(testDepartment);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).insertDepartment(argThat(dept -> {
            assertEquals(1, dept.getDeptId());
            assertEquals("技术部", dept.getDeptName());
            assertEquals(1, dept.getStatus());
            return true;
        }));
    }

    @Test
    void insertDepartment_Failed() {
        // 准备测试数据
        when(departmentMapper.insertDepartment(any(Department.class))).thenReturn(0);

        // 执行测试
        Response<Void> response = departmentService.insertDepartment(testDepartment);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("新增失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).insertDepartment(any(Department.class));
    }

    @Test
    void updateDepartment_Success() {
        // 准备测试数据
        when(departmentMapper.updateDepartment(any(Department.class))).thenReturn(1);

        // 执行测试
        Response<Void> response = departmentService.updateDepartment(testDepartment);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).updateDepartment(argThat(dept -> {
            assertEquals(1, dept.getDeptId());
            assertEquals("技术部", dept.getDeptName());
            assertEquals(1, dept.getStatus());
            return true;
        }));
    }

    @Test
    void updateDepartment_Failed() {
        // 准备测试数据
        when(departmentMapper.updateDepartment(any(Department.class))).thenReturn(0);

        // 执行测试
        Response<Void> response = departmentService.updateDepartment(testDepartment);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("更新失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).updateDepartment(any(Department.class));
    }

    @Test
    void deleteDepartment_Success() {
        // 准备测试数据
        when(departmentMapper.deleteDepartment(1)).thenReturn(1);

        // 执行测试
        Response<Void> response = departmentService.deleteDepartment(1);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).deleteDepartment(1);
    }

    @Test
    void deleteDepartment_Failed() {
        // 准备测试数据
        when(departmentMapper.deleteDepartment(999)).thenReturn(0);

        // 执行测试
        Response<Void> response = departmentService.deleteDepartment(999);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("删除失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(departmentMapper).deleteDepartment(999);
    }
} 