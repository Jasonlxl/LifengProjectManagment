package com.sd.lifeng.vo;


import lombok.Data;

/**
 * @Classname UserListVO
 * @Description
 * @Author bmr
 * @Date 2020/5/23 14:26:51
 */
@Data
public class UserListVO {
    private String telNo;
    private String realName;
    private String typeRemark;
    private Integer roleId;
    private String roleName;
    private String createDate;
}
