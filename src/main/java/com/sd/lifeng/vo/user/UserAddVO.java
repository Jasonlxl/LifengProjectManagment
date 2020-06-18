package com.sd.lifeng.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bmr
 * @classname UserAddVO
 * @description
 * @date 2020/5/27 8:46:51
 */
@Data
public class UserAddVO {

    @NotNull(message = "用户手机号不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    @NotNull(message = "用户类型不能为空")
    private int userTypeId;
    @NotNull(message = "角色不能为空")
    private int roleId;
    private String remark;
}
