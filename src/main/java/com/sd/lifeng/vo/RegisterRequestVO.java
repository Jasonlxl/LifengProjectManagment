package com.sd.lifeng.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Classname RegisterRequestVO
 * @Description TODO
 * @Date 2020/5/21 8:37:51
 * @Created by bmr
 */
@Data
public class RegisterRequestVO {
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "真实姓名不能为空")
    private String realName;
}
