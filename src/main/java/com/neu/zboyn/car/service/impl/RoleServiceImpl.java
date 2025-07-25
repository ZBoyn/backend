package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.mapper.RoleMapper;
import com.neu.zboyn.car.model.Role;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Response<PageResult<RoleDto>> getRoleList(Integer page, Integer pageSize,String roleId, String roleName, String roleKey, String status, String startTime, String endTime) {
        List<Role> roles = roleMapper.selectRoleList(roleId, roleName, roleKey, status, startTime, endTime);
        List<RoleDto> dtoList = new ArrayList<>();
        for (Role role : roles) {
            RoleDto dto = new RoleDto();
            BeanUtils.copyProperties(role, dto);
            dtoList.add(dto);
        }
        PageResult<RoleDto> result = new PageResult<>();
        result.setItems(dtoList);
        result.setTotal(dtoList.size());
        result.setPage(page == null ? 1 : page);
        result.setPageSize(pageSize == null ? 20 : pageSize);
        return Response.success(result);
    }

    @Override
    public Response<RoleDto> getRoleById(Long roleId) {
        Role role = roleMapper.selectRoleById(roleId);
        if (role == null) return Response.error(404, "角色不存在", "not found");
        RoleDto dto = new RoleDto();
        BeanUtils.copyProperties(role, dto);
        return Response.success(dto);
    }

    @Override
    public Response<Void> createRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int res = roleMapper.insertRole(role);
        return res > 0 ? Response.success(null) : Response.error(500, "新增失败", "insert error");
    }

    @Override
    public Response<Void> updateRole(String roleId,RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int res = roleMapper.updateRole(roleId, role);
        return res > 0 ? Response.success(null) : Response.error(500, "更新失败", "update error");
    }

    @Override
    public Response<Void> deleteRole(Long roleId) {
        int res = roleMapper.deleteRole(roleId);
        return res > 0 ? Response.success(null) : Response.error(500, "删除失败", "delete error");
    }

    @Override
    public Response<List<ShowRoleDto>> getRoleName() {
        List<ShowRoleDto> roles = roleMapper.getRole();
        return Response.success(roles);
    }

    @Override
    public Response<Void> changeRoleUser(String roleId, String userId) {
        roleMapper.changeRoleUser(roleId, userId);
        return new Response<>(0, null, "用户角色变更成功", "success");
    }

    @Override
    public Response<List<UserDto>> getRoleUsers(Long roleId) {
        List<User> users = roleMapper.getUsersByRoleId(roleId);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.setUsername(user.getUsername());
            dto.setNickname(user.getNickname());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setDeptId(user.getDeptId());
            dto.setStatus(user.getStatus());
            dto.setRemark(user.getRemark());
            userDtos.add(dto);
        }
        return Response.success(userDtos);
    }

    @Override
    public Response<Void> assignUsersToRole(Long roleId, List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Response.error(400, "用户ID不能为空", "userIds empty");
        }
        int res = roleMapper.insertUserRoles(roleId, userIds);
        return res > 0 ? Response.success(null) : Response.error(500, "分配用户失败", "assign error");
    }

    @Override
    public Response<Void> removeUsersFromRole(Long roleId, List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Response.error(400, "用户ID不能为空", "userIds empty");
        }
        int res = roleMapper.deleteUserRoles(roleId, userIds);
        return res > 0 ? Response.success(null) : Response.error(500, "移除用户失败", "remove error");
    }
}

