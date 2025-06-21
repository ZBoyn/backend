package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.LoginToken;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginTokenTest {

    @Test
    void testLoginToken() {
        String accessToken = "testToken";
        LoginToken loginToken = new LoginToken(accessToken);

        assertThat(loginToken.getAccessToken()).isEqualTo(accessToken);
    }
}
