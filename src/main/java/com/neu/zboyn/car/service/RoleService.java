package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.dto.UserDto;

import java.util.List;

public interface RoleService {
    Response<PageResult<RoleDto>> getRoleList(Integer page, Integer pageSize, String roleId, String roleName, String roleKey, String status, String startTime, String endTime);
    Response<RoleDto> getRoleById(Long roleId);
    Response<Void> createRole(RoleDto roleDto);
    Response<Void> updateRole(String roleId,RoleDto roleDto);
    Response<Void> deleteRole(Long roleId);
    Response<List<ShowRoleDto>> getRoleName();

    Response<Void> changeRoleUser(String roleId, String userId);

    Response<List<UserDto>> getRoleUsers(Long roleId);
    Response<Void> assignUsersToRole(Long roleId, List<Long> userIds);
    Response<Void> removeUsersFromRole(Long roleId, List<Long> userIds);
}
