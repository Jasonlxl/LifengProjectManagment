package com.sd.lifeng.service;

import com.sd.lifeng.domain.UserDO;

/**
 * @Classname IBaseService
 * @Description
 * @Author bmr
 * @Date 2020/5/24 8:59:51
 */
public interface ICommonService {
    
    /**
     * @Description 获取登录用户信息
     * @Auther bmr
     * @Date 2020/5/24 : 9:03 :51 
     * @Return com.sd.lifeng.domain.UserDO
     */
    UserDO getUserInfo();
    
    /**
     * @Description 获取登录用户id
     * @Auther bmr
     * @Date 2020/5/24 : 9:04 :51 
     * @Return java.lang.Integer
     */
    Integer getUserId();
}
