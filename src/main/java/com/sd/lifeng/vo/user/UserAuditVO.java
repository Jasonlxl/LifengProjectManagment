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

    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "审核状态不能为空")
    private Integer status;
}
