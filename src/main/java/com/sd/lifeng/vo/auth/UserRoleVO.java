package com.sd.lifeng.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bmr
 * @classname UserRoleVO
 * @description
 * @date 2020/5/26 17:30:51
 */
@Data
public class UserRoleVO {
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "角色id不能为空")
    private Integer roleId;
}
