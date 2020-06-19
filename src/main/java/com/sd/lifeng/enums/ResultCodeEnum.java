package com.sd.lifeng.enums;

import lombok.Getter;

/**
 * @Classname ResultCodeEnum
 * @Description 返回码枚举值
 * @author bmr
 * @time 2020-04-14 14:09
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS("0","成功"),
    SERVER_EXCEPTION("-1","服务异常"),
    PARAM_ERROR("10","参数错误"),
    TOKEN_MISS("11","token缺失，请传递token"),
    TOKEN_ILLEGAL("12","token非法"),
    TOKEN_INVALID("13","token失效"),
    USER_HAS_REGISTERED("14","用户名已注册"),
    USER_NOT_EXIST("15","用户不存在"),
    USER_HAS_AUDITED("18","用户已审核通过，请勿重复操作"),
    LOGIN_ERROR("16","用户名密码错误"),
    ROLE_NOT_EXIST("17","角色不存在"),
    USER_ROLE_NOT_EXIST("18","用户未分配该角色，不能移除"),
    ROLE_RESOURCE_NOT_EXIST("18","角色未分配该资源，不能移除"),
    USER_PASSWORD_ERROR("18","用户密码不正确"),
    ONLY_MANAGER_CAN_OPERATE("17","非系统管理员，权限不足"),
    DATA_BASE_UPDATE_ERROR("100","数据库更新数据失败");
    private String code;
    private String msg;


    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
