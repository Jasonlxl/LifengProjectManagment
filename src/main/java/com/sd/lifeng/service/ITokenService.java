package com.sd.lifeng.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname ITokenService
 * @Description
 * @Author bmr
 * @Date 2020/5/23 13:26:51
 */
public interface ITokenService {

    /**
     * 生成token
     * @param str token中要放入的值
     * @return
     */
    String createToken(String str);
    /**
     * 获取token中的userId
     * @return
     */
    int getUserId();


}
