package com.neu.zboyn.car.service.impl;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.mapper.UserManageMapper;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.UserService;
import com.neu.zboyn.car.util.BCryptUtil;
import com.neu.zboyn.car.service.SysConfigService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserManageMapper userManageMapper;

    @Autowired
    private BCryptUtil bCryptUtil;

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public Response<PageResult<User>> getUserList(int page, int pageSize, String userId, String username, String nickname, Long deptId, String phoneNumber, Integer status, String startTime, String endTime) {
        int offset = (page - 1) * pageSize;
        List<User> userList = userManageMapper.selectUserList(offset, pageSize, userId, username, nickname, deptId, phoneNumber, status, startTime, endTime);
        long total = userManageMapper.selectUserCount(userId, username, nickname, deptId, phoneNumber, status, startTime, endTime);
        PageResult<User> pageResult = new PageResult<>(userList, total, page, pageSize);
        return new Response<>(0, pageResult, "获取成功", "success");
    }

    @Override
    public Response<Void> createUser(UserDto userDto) {
        userManageMapper.create(userDto);
        return new Response<>(0, null, "用户创建成功", "success");
    }

    @Override
    public Response<Void> updateUser(String userId, UserDto userDto) {
        userManageMapper.update(userId, userDto);
        return new Response<>(0, null, "<UNK>", "success");
    }

    @Override
    public Response<Void> deleteUser(String userId) {
        userManageMapper.delete(userId);
        return new Response<>(0, null, "<UNK>", "success");
    }

    @Override
    @Transactional
    public Response<Void> resetPassword(String userId) {
        String initPassword = "123456";
        if (sysConfigService != null) {
            com.neu.zboyn.car.model.Sysconfig config = sysConfigService.getByConfigKey("sys.user.initPassword");
            if (config != null && config.getConfigValue() != null && !config.getConfigValue().isEmpty()) {
                initPassword = config.getConfigValue();
            }
        }
        String hashPassword = bCryptUtil.hashPassword(initPassword);
        userManageMapper.resetPassword(userId, hashPassword);
        return new Response<>(0, null, "密码重置成功", "success");
    }

    @Override
    public Response<Void> changeUserRole(String userId, String roleId) {
        userManageMapper.changeUserRole(userId, roleId);
        return new Response<>(0, null, "用户角色变更成功", "success");
    }

    @Override
    public Response<User> getUserByCreatorId(String creatorId) {
        User user = userManageMapper.findByCreatorId(creatorId);
        if (user == null) {
            return Response.error(404, "用户不存在", "not found");
        }
        return Response.success(user);
    }

}
