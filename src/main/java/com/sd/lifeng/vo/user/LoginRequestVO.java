package com.sd.lifeng.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * @Classname LoginRequestVO
 * @Description TODO
 * @Date 2020/5/20 8:32:51
 * @Created by bmr
 */
@Data
public class LoginRequestVO {
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    private String password;

    private String clientid;

}
