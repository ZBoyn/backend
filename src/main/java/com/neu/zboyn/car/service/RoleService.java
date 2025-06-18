package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.PageResult;

public interface RoleService {
    Response<PageResult<RoleDto>> getRoleList(Integer page, Integer pageSize, String roleName, String roleKey, String status, String startTime, String endTime);
    Response<RoleDto> getRoleById(Long roleId);
    Response<Void> createRole(RoleDto roleDto);
    Response<Void> updateRole(RoleDto roleDto);
    Response<Void> deleteRole(Long roleId);
}
