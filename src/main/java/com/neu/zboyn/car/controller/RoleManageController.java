package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.service.RoleService;
import com.neu.zboyn.car.dto.RoleDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 角色管理模块
 */
@RestController
@RequestMapping("/api/system/role")
public class RoleManageController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     * @param page 当前页码，默认为1
     * @param pageSize 每页条数，默认为20
     * @param roleName 角色名称
     * @param roleKey 角色权限标识
     * @param status 角色状态（0正常，1停用）
     * @return 角色列表和分页信息
     */
    @RequestMapping("/list")
    public Response<PageResult<RoleDto>> getRoleList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String roleKey,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    ) {
        return roleService.getRoleList(page, pageSize, roleName, roleKey, status, startTime, endTime);
    }

    /**
     * 根据角色ID获取角色信息
     * @param roleId 角色ID
     * @return 角色信息
     */
    @GetMapping("/{roleId}")
    public Response<RoleDto> getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }

    /**
     * 创建新角色
     * @param roleDto 角色信息
     * @return 创建结果
     */
    @PostMapping("")
    public Response<Void> create(@RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    /**
     * 更新角色信息
     * @param roleDto 角色信息
     * @return 更新结果
     */
    @PutMapping("")
    public Response<Void> update(@RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return 删除结果
     */
    @DeleteMapping("/{roleId}")
    public Response<Void> delete(@PathVariable Long roleId) {
        return roleService.deleteRole(roleId);
    }
}
