package com.sd.lifeng.vo.auth;

import lombok.Data;

/**
 * @author bmr
 * @Classname RoleVO
 * @Description
 * @Date 2020/5/25 8:36:51
 */
@Data
public class RoleVO {
    private Integer id;
    private String roleName;
    private Integer systemManager;
}
