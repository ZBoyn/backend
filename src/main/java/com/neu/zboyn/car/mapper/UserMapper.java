package com.neu.zboyn.car.mapper;


import com.neu.zboyn.car.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User Login(@Param("username")String username);
    User findByid(@Param("userId")Integer userId);
}
