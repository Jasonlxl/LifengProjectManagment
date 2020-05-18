package com.sd.lifeng.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenUtil {

    /**
     *
     * @param id 用户id  使用jwt的规定属性aud存放用户id
     * @param sec 秘钥
     * @return
     */
    public static String getToken(String id,String sec){
        return JWT.create().withAudience(id).sign(Algorithm.HMAC256(sec));
    }
}
