package com.neu.zboyn.car.service.impl;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.UserManageMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public Response<PageResult<User>> getUserList(int page, int pageSize, String userId, String username, String nickname, Long deptId, String phoneNumber, Integer status, String startTime, String endTime) {
        int offset = (page - 1) * pageSize;
        List<User> userList = userManageMapper.selectUserList(offset, pageSize, userId, username, nickname, deptId, phoneNumber, status, startTime, endTime);
        long total = userManageMapper.selectUserCount(userId, username, nickname, deptId, phoneNumber, status, startTime, endTime);
        PageResult<User> pageResult = new PageResult<>(userList, total, page, pageSize);
        return new Response<>(0, pageResult, "获取成功", "success");
    }
}
