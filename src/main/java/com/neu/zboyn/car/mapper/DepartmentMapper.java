package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<Department> getDepartment();
}
