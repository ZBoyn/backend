package com.neu.zboyn.car.controller;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理模块
 */
@RestController
@RequestMapping("/api/system/user")
public class UserManageController {
    @Autowired
    private UserService userService;

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

}
