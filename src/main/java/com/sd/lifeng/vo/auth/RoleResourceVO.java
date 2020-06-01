package com.sd.lifeng.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleResourceVO {
    @NotNull(message = "角色id不能为空")
    private Integer roleId;
    @NotNull(message = "资源id不能为空")
    private Integer resourceId;
}
