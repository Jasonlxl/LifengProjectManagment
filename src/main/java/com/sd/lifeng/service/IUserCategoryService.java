package com.sd.lifeng.service;

import com.sd.lifeng.domain.UserDO;

public interface IUserCategoryService {


    UserDO login(String userName,String password);

    /*
    密码检查
     */
    public boolean passwdCheck(String passwd);

    UserDO findUserById(int userId);
}
