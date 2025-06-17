package com.neu.zboyn.car.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;

import java.util.Date;

/**
 * JWT 工具类，用于生成符合特定格式的Token.
 */
public class JWTUtil {
    // 密钥
    public static final String SECRET_KEY = "pvXXytcvXzLOsmyt";

    // 令牌过期时间 (毫秒)，这里设置为1小时
    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;

    /**
     * 根据用户信息生成JWT令牌.
     *
     * @param id 用户ID
     * @param username 用户名 (对应jwt.io中的'name')
     * @param realName 真实姓名 (对应jwt.io中的'sub')
     * @return 生成的JWT令牌字符串
     */
    public static String generateToken(Integer id, String username, String realName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Date expirationDate = new Date(nowMillis + EXPIRATION_TIME_MS);

            String token = JWT.create()
                    .withClaim("id", id)
                    .withClaim("name", username)
                    .withSubject(realName)
                    .withIssuedAt(now)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);

            return token;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
