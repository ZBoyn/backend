package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginRequestTest {

    @Test
    void testLoginRequestSetters() {
        // 测试无参构造器+setter方法
        LoginRequest request = new LoginRequest();

        String selectAccount = "admin";
        String username = "testUser";
        String password = "password123";
        Boolean captcha = true;

        request.setSelectAccount(selectAccount);
        request.setUsername(username);
        request.setPassword(password);
        request.setCaptcha(captcha);

        assertThat(request.getSelectAccount()).isEqualTo(selectAccount);
        assertThat(request.getUsername()).isEqualTo(username);
        assertThat(request.getPassword()).isEqualTo(password);
        assertThat(request.getCaptcha()).isEqualTo(captcha);
    }

    @Test
    void testLoginRequestToString() {
        // 测试toString方法（由@Data生成）
        LoginRequest request = new LoginRequest();
        request.setSelectAccount("admin");
        request.setUsername("testUser");
        request.setPassword("password123");
        request.setCaptcha(true);

        String toString = request.toString();

        assertThat(toString).contains("LoginRequest");
        assertThat(toString).contains("selectAccount=admin");
        assertThat(toString).contains("username=testUser");
        assertThat(toString).contains("password=password123");
        assertThat(toString).contains("captcha=true");
    }

    @Test
    void testLoginRequestEqualsAndHashCode() {
        // 测试equals和hashCode方法（由@Data生成）
        LoginRequest request1 = new LoginRequest();
        request1.setSelectAccount("admin");
        request1.setUsername("testUser");
        request1.setPassword("password123");
        request1.setCaptcha(true);

        LoginRequest request2 = new LoginRequest();
        request2.setSelectAccount("admin");
        request2.setUsername("testUser");
        request2.setPassword("password123");
        request2.setCaptcha(true);

        LoginRequest request3 = new LoginRequest();
        request3.setSelectAccount("user");
        request3.setUsername("differentUser");
        request3.setPassword("password456");
        request3.setCaptcha(false);

        // 相等性测试
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // 不相等测试
        assertThat(request1).isNotEqualTo(request3);
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());
    }
}