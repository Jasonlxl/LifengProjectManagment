package com.sd.lifeng.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenUtil {

    /**
     *
     * @param id 用户id  使用jwt的规定属性aud存放用户id
     * @param sec 秘钥
     * @param expireTime 有效期
     * @return
     */
    public static String createToken(String id, String sec, String expireTime){
        return JWT.create().withAudience(id,expireTime).sign(Algorithm.HMAC256(sec));
    }

}
