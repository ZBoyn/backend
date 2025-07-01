package com.neu.zboyn.car.mapper;


import com.neu.zboyn.car.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    User Login(@Param("username")String username);
    User findByid(@Param("userId")Integer userId);

    void insertUser(@Param("username")String username, @Param("nickname")String nickname,
                  @Param("password")String password, @Param("deptId")int deptId,
                  @Param("phoneNumber")String phoneNumber);

    void insertUserRole(@Param("userId") Integer userId);

    String getLatestUserId();

}
