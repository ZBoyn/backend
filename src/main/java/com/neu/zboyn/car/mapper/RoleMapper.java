package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> selectRoleList(@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("roleKey") String roleKey, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Role selectRoleById(@Param("roleId") Long roleId);
    int insertRole(Role role);
    int updateRole(Role role);
    int deleteRole(@Param("roleId") Long roleId);
} 