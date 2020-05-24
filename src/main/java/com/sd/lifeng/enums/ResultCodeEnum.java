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
    LOGIN_ERROR("16","用户名密码错误"),
    ONLY_MANAGER_CAN_RESET_PASSWORD("17","非系统管理员，不可重置用户密码"),
    DATA_BASE_UPDATE_ERROR("100","数据库更新数据失败");
    private String code;
    private String msg;


    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
