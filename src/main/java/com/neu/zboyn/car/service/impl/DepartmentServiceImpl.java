package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.DepartmentMapper;
import com.neu.zboyn.car.model.Department;
import com.neu.zboyn.car.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Response<List<Department>> getDepartmentinfo() {
        List<Department> departments = departmentMapper.getDepartment();
        return Response.success(departments);
    }

    @Override
    public Response<Void> insertDepartment(Department department) {
        int res = departmentMapper.insertDepartment(department);
        return res > 0 ? Response.success(null) : Response.error(500, "新增失败", "insert error");
    }

    @Override
    public Response<Void> updateDepartment(Department department) {
        int res = departmentMapper.updateDepartment(department);
        return res > 0 ? Response.success(null) : Response.error(500, "更新失败", "update error");
    }

    @Override
    public Response<Void> deleteDepartment(int deptId) {
        int res = departmentMapper.deleteDepartment(deptId);
        return res > 0 ? Response.success(null) : Response.error(500, "删除失败", "delete error");
    }

    @Override
    public Response<List<Department>> getDepartmentinfo(String deptName, Integer status) {
        List<Department> departments = departmentMapper.getZBYDepartment(deptName, status);
        return Response.success(departments);
    }
}
