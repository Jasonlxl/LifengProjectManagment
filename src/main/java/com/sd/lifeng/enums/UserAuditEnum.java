package com.sd.lifeng.enums;

import lombok.Getter;

/**
 * @Classname UserAuditEnum
 * @Description 用户注册审核状态枚举
 * @Author bmr
 * @Date 2020/5/22 8:39:51
 */

@Getter
public enum UserAuditEnum {
    /** 待审核状态 */
    PRE_AUDIT(0,"待审核"),
    AUDIT_SUCCESS(1,"审核通过"),
    AUDIT_FAIL(2,"审核失败");

    private Integer value;
    private String  remark;

    UserAuditEnum(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }
}
