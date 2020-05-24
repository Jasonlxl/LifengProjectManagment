package com.sd.lifeng.service;

import com.sd.lifeng.vo.UserTypeVO;

import java.util.List;

/**
 * @Classname IUserTypeService
 * @Description
 * @Author bmr
 * @Date 2020/5/24 8:43:51
 */
public interface IUserTypeService {
    /**
     * @Description 获取用户类型列表
     * @Auther bmr
     * @Date 2020/5/24 : 8:59 :51
     * @Return java.util.List<com.sd.lifeng.vo.UserTypeVO>
     */
    List<UserTypeVO> getUserTypeList();
}
