package com.sd.lifeng.enums;

import lombok.Getter;

/**
 * @Classname UserTypeEnum
 * @Description
 * @Author bmr
 * @Date 2020/5/23 14:35:51
 */
@Getter
public enum  UserTypeEnum {
    PARTY_A(1,"甲方"),
    PARTY_LIFENG(2,"砺峰方");

    private Integer value;
    private String  remark;

    UserTypeEnum(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public static String getRemark(int code){
        String remark="";
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()){
            if(userTypeEnum.getValue() == code){
                remark = userTypeEnum.getRemark();
                break;
            }
        }

        return remark;
    }
}
