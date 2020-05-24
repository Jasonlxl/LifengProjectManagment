package com.sd.lifeng.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Classname ResetPasswordVO
 * @Description
 * @Author bmr
 * @Date 2020/5/24 9:10:51
 */
@Data
public class ResetPasswordVO {

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotEmpty(message = "用户新密码不能为空")
    private String newPassword;
}
