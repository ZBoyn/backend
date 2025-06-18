package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Department;
import com.neu.zboyn.car.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@RequestMapping("/api/system/dept")
public class DepartmentManageController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取部门列表
     * @param deptName 部门名称
     * @param status 部门状态
     * @return 部门列表响应
     */
    @RequestMapping("/list")
    public Response<List<Department>> getDepartments(
            @RequestParam(value = "deptName", required = false) String deptName,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return departmentService.getDepartmentinfo(deptName, status);
    }

    @PostMapping("")
    public Response<Void> create(@RequestBody Department department) {
        return departmentService.insertDepartment(department);
    }

    @PutMapping("")
    public Response<Void> update(@RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/{deptId}")
    public Response<Void> delete(@PathVariable int deptId) {
        return departmentService.deleteDepartment(deptId);
    }
}
