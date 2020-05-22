package com.sd.lifeng.domain;

import lombok.Data;

/**
 * @Author Bmr
 * @Classname RegiserDO
 * @Description 用户注册DO
 * @Date 2020/5/22 8:11:51
 */
@Data
public class RegisterDO {
    private Integer id;
    private String telNo;
    private String password;
    private String salt;
    private String realName;
    private Integer status;
    private String createDate;
    private String remark;
}
