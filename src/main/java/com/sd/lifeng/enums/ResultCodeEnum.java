package com.sd.lifeng.enums;

import lombok.Getter;

/**
 * @author bmr
 * @time 2020-04-14 14:09
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS("0","成功"),
    SERVER_EXCEPTION("-1","服务异常"),
    DATA_BASE_UPDATE_ERROR("10","数据库更新数据失败");
    private String code;
    private String msg;


    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
