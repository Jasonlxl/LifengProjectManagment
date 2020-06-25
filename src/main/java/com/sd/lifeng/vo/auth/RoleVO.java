package com.sd.lifeng.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bmr
 * @Classname RoleVO
 * @Description
 * @Date 2020/5/25 8:36:51
 */
@Data
public class RoleVO {
    private int id;
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    private int systemManager;
}
