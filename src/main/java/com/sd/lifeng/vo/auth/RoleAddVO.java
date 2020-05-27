package com.sd.lifeng.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author bmr
 * @classname RoleAddVO
 * @description
 * @date 2020/5/25 9:04:51
 */
@Data
public class RoleAddVO {
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    private int systemManager;
}
