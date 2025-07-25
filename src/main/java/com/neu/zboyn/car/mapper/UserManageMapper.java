package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.dto.UserDto;
import com.neu.zboyn.car.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserManageMapper {
    List<User> selectUserList(
        @Param("offset") int offset,
        @Param("pageSize") int pageSize,
        @Param("userId") String userId,
        @Param("username") String username,
        @Param("nickname") String nickname,
        @Param("deptId") Long deptId,
        @Param("phoneNumber") String phoneNumber,
        @Param("status") Integer status, // char(1)
        @Param("startTime") String startTime,
        @Param("endTime") String endTime
    );

    long selectUserCount(
            @Param("userId") String userId,
            @Param("username") String username,
            @Param("nickname") String nickname,
            @Param("deptId") Long deptId,
            @Param("phoneNumber") String phoneNumber,
            @Param("status") Integer status, // char(1)
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    void create(UserDto userDto);

    void update(@Param("userId") String userId, @Param("userDto")UserDto userDto);

    void delete(String userId);

    void resetPassword(@Param("userId") String userId, @Param("newPassword") String newPassword);

    void changeUserRole(String userId, String roleId);

    User findByCreatorId(String creatorId);
}
