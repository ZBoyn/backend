package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Department;

import java.util.List;

public interface DepartmentService
{
    Response<List<Department>> getDepartmentinfo();
}
