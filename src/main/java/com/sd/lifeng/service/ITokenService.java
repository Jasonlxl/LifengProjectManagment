package com.sd.lifeng.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname ITokenService
 * @Description TODO
 * @Author bmr
 * @Date 2020/5/23 13:26:51
 */
public interface ITokenService {
    /**
     * 获取token中的userId
     * @return
     */
    int getUserId();
}
