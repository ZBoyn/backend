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

    /**
     * 创建新部门
     * @param department 部门信息
     * @return 创建结果响应
     */
    @PostMapping("")
    public Response<Void> create(@RequestBody Department department) {
        return departmentService.insertDepartment(department);
    }

    /**
     * 更新部门信息
     * @param department 部门信息
     * @return  更新结果响应
     */
    @PutMapping("")
    public Response<Void> update(@RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }

    /**
     * 删除部门
     * @param deptId 部门ID
     * @return 删除结果响应
     */
    @DeleteMapping("/{deptId}")
    public Response<Void> delete(@PathVariable int deptId) {
        return departmentService.deleteDepartment(deptId);
    }
}
