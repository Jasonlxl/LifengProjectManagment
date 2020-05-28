package com.sd.lifeng.vo.user;

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

    private String newPassword;
}
