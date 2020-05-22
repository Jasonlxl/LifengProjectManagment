package com.sd.lifeng.service;

import com.sd.lifeng.domain.UserDO;

public interface IUserCategoryService {

    /**
     * @Description 用户注册
     * @param userName 用户名
     * @param password 密码
     * @Auther bmr
     * @Date 2020/5/22 : 8:22 :51
     * @Return void
     */
    void register(String userName,String password,String realName);

    UserDO login(String userName,String password);

    /*
    密码检查
     */
     boolean passwdCheck(String passwd);

    UserDO findUserById(int userId);
}
