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
}
