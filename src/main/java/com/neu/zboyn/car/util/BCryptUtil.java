package com.neu.zboyn.car.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {

    // BCrypt加密
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    /**
     * 将明文密码加密为 BCrypt 哈希值。
     * 每次调用生成的哈希值都不同，因为盐值(salt)是随机生成的并包含在哈希结果中。
     *
     * @param plainTextPassword 需要加密的明文密码
     * @return BCrypt 哈希字符串
     */
    public String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null) {
            return null;
        }
        return encoder.encode(plainTextPassword);
    }

    /**
     * 校验明文密码是否与 BCrypt 哈希值匹配。
     *
     * @param plainTextPassword 用户输入的明文密码
     * @param hashedPassword    数据库中存储的 BCrypt 哈希值
     * @return 如果密码匹配则返回 true，否则返回 false
     */
    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        return encoder.matches(plainTextPassword, hashedPassword);
    }

}
