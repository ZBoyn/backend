package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.dto.ShowRoleDto;
import com.neu.zboyn.car.model.Role;
import com.neu.zboyn.car.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> selectRoleList(@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("roleKey") String roleKey, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Role selectRoleById(@Param("roleId") Long roleId);
    int insertRole(Role role);
    int updateRole(String roleId,Role role);
    int deleteRole(@Param("roleId") Long roleId);
    List<ShowRoleDto> getRole();

    void changeRoleUser(String roleId, String userId);

    List<ShowRoleDto> getRolesByUserId(@Param("userId") String userId);

    // 根据角色ID查找用户
    List<User> getUsersByRoleId(@Param("roleId") Long roleId);
    // 批量插入user_role
    int insertUserRoles(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);
    // 批量删除user_role
    int deleteUserRoles(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    // 根据用户ID查找角色英文标识（role_key）
    List<String> getRoleNamesByUserId(@Param("userId") Integer userId);
}