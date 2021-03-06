package com.sd.lifeng.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Classname ChangePasswordVO
 * @Description
 * @Author bmr
 * @Date 2020/5/24 9:11:51
 */
@Data
public class ChangePasswordVO {

    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
