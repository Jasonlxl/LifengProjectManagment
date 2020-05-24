package com.sd.lifeng.service;

import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.vo.LoginResponseVO;
import com.sd.lifeng.vo.RegisterResponseVO;
import com.sd.lifeng.vo.UserListVO;

import java.util.List;
import java.util.Map;

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

    /**
     * @Description 用户登录
     * @param userName 用户名
     * @param password 密码
     * @Auther bmr
     * @Date 2020/5/24 : 8:50 :51
     * @Return java.util.Map
     */
    LoginResponseVO login(String userName, String password);

    /**
     * @Description 根据用户id获取用户信息
     * @param userId 用户id
     * @Auther bmr
     * @Date 2020/5/24 : 8:50 :51
     * @Return com.sd.lifeng.domain.UserDO
     */
    UserDO findUserById(int userId);

    /**
     * @Description 修改密码
     * @param newPassword 新密码
     * @Auther bmr
     * @Date 2020/5/24 : 8:51 :51
     * @Return void
     */
    void changePassword(String newPassword);

    /**
     * @Description 重置用户密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @Auther bmr
     * @Date 2020/5/24 : 8:55 :51
     * @Return void
     */
    void resetPassword(int userId,String newPassword);

    /**
     * @Description 获取用户注册列表
     * @param status 审核状态  不填默认查询全部
     * @Auther bmr
     * @Date 2020/5/24 : 8:54 :51
     * @Return java.util.List<com.sd.lifeng.vo.RegisterResponseVO>
     */
    List<RegisterResponseVO> getRegisterList(String status);

    /**
     * @Description 获取用户列表
     * @Auther bmr
     * @Date 2020/5/24 : 8:54 :51
     * @Return java.util.List<com.sd.lifeng.vo.UserListVO>
     */
    List<UserListVO> getUserList();
}
