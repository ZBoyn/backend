package com.neu.zboyn.car.Dto;



import com.neu.zboyn.car.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest {

    @Test
    void testUserDtoConstructorAndGetters() {
        // 测试全参构造器及getter方法
        String username = "testUser";
        String nickname = "Test Nick";
        String phoneNumber = "13800138000";
        Integer deptId = 101;
        Integer status = 1; // 启用
        String remark = "测试用户";

        UserDto user = new UserDto();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPhoneNumber(phoneNumber);
        user.setDeptId(deptId);
        user.setStatus(status);
        user.setRemark(remark);

        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(user.getDeptId()).isEqualTo(deptId);
        assertThat(user.getStatus()).isEqualTo(status);
        assertThat(user.getRemark()).isEqualTo(remark);
    }

    @Test
    void testUserDtoToString() {
        // 测试toString方法（由@Data生成）
        UserDto user = new UserDto();
        user.setUsername("testUser");
        user.setNickname("Test Nick");
        user.setStatus(1);

        String toString = user.toString();

        assertThat(toString).contains("UserDto");
        assertThat(toString).contains("username=testUser");
        assertThat(toString).contains("nickname=Test Nick");
        assertThat(toString).contains("status=1");
    }

    @Test
    void testUserDtoEqualsAndHashCode() {
        // 测试equals和hashCode方法（由@Data生成）
        UserDto user1 = new UserDto();
        user1.setUsername("testUser");
        user1.setStatus(1);

        UserDto user2 = new UserDto();
        user2.setUsername("testUser");
        user2.setStatus(1);

        UserDto user3 = new UserDto();
        user3.setUsername("anotherUser");
        user3.setStatus(0);

        // 相等性测试
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        // 不相等测试
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode());
    }
}