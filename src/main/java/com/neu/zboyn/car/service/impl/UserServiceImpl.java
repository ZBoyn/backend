package com.neu.zboyn.car.service.impl;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.UserManageMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public Response<PageResult<User>> getUserList(int page, int pageSize, Long userId, String username, String nickname, Long deptId, String phoneNumber, String status, String createTime) {
        int offset = (page - 1) * pageSize;
        List<User> userList = userManageMapper.selectUserList(offset, pageSize, userId, username, nickname, deptId, phoneNumber, status, createTime);
        long total = userManageMapper.selectUserCount(userId, username, nickname, deptId, phoneNumber, status, createTime);
        PageResult<User> pageResult = new PageResult<>(userList, total, page, pageSize);
        return new Response<>(0, pageResult, "登录成功", null);
    }
}
