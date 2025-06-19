package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Department;

import java.util.List;

public interface DepartmentService
{
    Response<List<Department>> getDepartmentinfo();
    Response<List<Department>> getDepartmentinfo(String deptName, Integer status);
    Response<Void> insertDepartment(Department department);
    Response<Void> updateDepartment(Department department);
    Response<Void> deleteDepartment(int deptId);
}
