package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

    @Test
    void testRegisterRequest() {
        String username = "testUser";
        String nickname = "testNick";
        String password = "testPass";
        int deptId = 1;
        String phoneNumber = "1234567890";
        Date createTime = new Date();
        RegisterRequest request = new RegisterRequest();

        assertThat(request.getUsername()).isEqualTo(username);
        assertThat(request.getNickname()).isEqualTo(nickname);
        assertThat(request.getPassword()).isEqualTo(password);
        assertThat(request.getDeptId()).isEqualTo(deptId);
        assertThat(request.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(request.getCreateTime()).isEqualTo(createTime);
    }
}
