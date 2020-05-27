package com.sd.lifeng.vo.user;

import lombok.Data;

/**
 * @Classname RegisterResponseVO
 * @Description
 * @Author bmr
 * @Date 2020/5/23 14:01:51
 */
@Data
public class RegisterResponseVO {

    private String telNo;
    private String realName;
    private Integer status;
    private String statusDescription;
    private String createDate;
}
