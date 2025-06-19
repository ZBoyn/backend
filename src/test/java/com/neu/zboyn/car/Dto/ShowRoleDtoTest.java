package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.ShowRoleDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShowRoleDtoTest {

    @Test
    void testShowRoleDto() {
        int roleId = 1;
        String roleName = "Admin";
        ShowRoleDto showRoleDto = new ShowRoleDto(roleId, roleName);

        assertThat(showRoleDto.getRoleId()).isEqualTo(roleId);
        assertThat(showRoleDto.getRoleName()).isEqualTo(roleName);
    }
}