package com.sd.lifeng.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bmr
 * @classname UserAuditVO
 * @description
 * @date 2020/5/27 8:46:51
 */
@Data
public class UserAuditVO {

    @NotNull(message = "用户手机号不能为空")
    private String userName;
    @NotNull(message = "审核状态不能为空")
    private Integer status;
    private int userTypeId;
    private int roleId;
}
