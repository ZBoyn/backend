package com.neu.zboyn.car.controller;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.model.Department;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.DepartmentService;
import com.neu.zboyn.car.service.RoleService;
import com.neu.zboyn.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 用户管理模块
 */
@RestController
@RequestMapping("/api/system/user")
public class UserManageController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;


    /**
     * 根据分页和页大小显示所有用户列表
     * @param page 页数
     * @param pageSize 页大小
     * @param id 用户id
     * @param username 用户名称
     * @param nickname 用户昵称
     * @param deptId 部门id
     * @param phoneNumber 手机号码
     * @param status 状态
     * @param startTime 创建时间开始
     * @param endTime 创建时间结束
     * @return 用户列表
     */
    @RequestMapping("/list")
    public Response<PageResult<User>> getUserList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    ){
        // 设置默认值
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        // 调用Service
        return userService.getUserList(page, pageSize, id, username, nickname, deptId, phoneNumber, status, startTime, endTime);
    }

    /**
     * 创建用户
     * @param userDto 用户数据传输对象
     * @return 响应结果
     */
    @RequestMapping("")
    public Response<Void> create(
            @RequestBody UserDto userDto){
        // 调用Service
        return userService.createUser(userDto);
    }

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param userDto 用户数据传输对象
     * @return 响应结果
     */
    @PutMapping("/{userId}")
    public Response<Void> updateUser(
            @PathVariable String userId,
            @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 响应结果
     */
    @DeleteMapping("/{userId}")
    public Response<Void> deleteUser(
            @PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 更新用户密码
     * @param userId 用户ID
     * @return 响应结果
     */
    @PutMapping("/{userId}/reset-password")
    public Response<Void> resetPassword(
            @PathVariable String userId) {
        return userService.resetPassword(userId);
    }

    /**
     * 更新用户角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 响应结果
     */
    @PutMapping("/{userId}/change-role")
    public Response<Void> changeRole(
            @PathVariable String userId,
            @RequestParam String roleId
    ) {
        return userService.changeUserRole(userId, roleId);
    }

    /**
     * 显示所有角色
     * @return role_id, role_name
     */
    @GetMapping("/roles")
    @Cacheable("userManage_roles")
    public Response<List<ShowRoleDto>> getAllRoles() {
        return roleService.getRoleName();
    }

    @GetMapping("/department")
    @Cacheable("userManage_department")
    public Response<List<Department>> getAllDepartments(){
        return departmentService.getDepartmentinfo();
    }

    /**
     * 获取指定用户的当前角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    @GetMapping("/{userId}/roles")
    @Cacheable(value = "userManage_user_roles", key = "#userId")
    public Response<List<ShowRoleDto>> getUserRoles(@PathVariable String userId) {
        return userService.getUserRolesByUserId(userId);
    }

}
