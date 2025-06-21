package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.RoleDto;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
class RoleDtoTest {

    @Test
    void testRoleDto() {
        String roleId = "1";
        String roleName = "Admin";
        String roleKey = "admin_key";
        Integer roleSort = 1;
        String dataScope = "all";
        String status = "active";
        Date createTime = new Date();
        Date updateTime = new Date();
        String remark = "System administrator";

        RoleDto roleDto = new RoleDto(roleId, roleName, roleKey, roleSort, dataScope, status, createTime, updateTime, remark);

        assertThat(roleDto.getRoleId()).isEqualTo(roleId);
        assertThat(roleDto.getRoleName()).isEqualTo(roleName);
        assertThat(roleDto.getRoleKey()).isEqualTo(roleKey);
        assertThat(roleDto.getRoleSort()).isEqualTo(roleSort);
        assertThat(roleDto.getDataScope()).isEqualTo(dataScope);
        assertThat(roleDto.getStatus()).isEqualTo(status);
        assertThat(roleDto.getCreateTime()).isEqualTo(createTime);
        assertThat(roleDto.getUpdateTime()).isEqualTo(updateTime);
        assertThat(roleDto.getRemark()).isEqualTo(remark);
    }
}
