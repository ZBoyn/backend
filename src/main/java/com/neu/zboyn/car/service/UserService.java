package com.neu.zboyn.car.service;


import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.model.User;

import java.util.List;

public interface UserService {

    Response<PageResult<User>> getUserList(
            int page,
            int pageSize,
            String userId,
            String username,
            String nickname,
            Long deptId,
            String phoneNumber,
            Integer status,
            String startTime,
            String endTime
    );


    Response<Void> createUser(UserDto userDto);

    Response<Void> updateUser(String userId, UserDto userDto);

    Response<Void> deleteUser(String userId);

    Response<Void> resetPassword(String userId);

    Response<Void> changeUserRole(String userId, String roleId);


    Response<User> getUserByCreatorId(String creatorId);

    Response<List<ShowRoleDto>> getUserRolesByUserId(String userId);
}
