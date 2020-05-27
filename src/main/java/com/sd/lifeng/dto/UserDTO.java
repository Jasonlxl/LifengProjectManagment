package com.sd.lifeng.dto;

import lombok.Data;

/**
 * @Classname UserDTO
 * @Description TODO
 * @Date 2020/5/21 9:00:51
 * @Created by bmr
 */
@Data
public class UserDTO {
    private String userName;

    private String password;

    private Integer type;

    private String realName;

    private String salt;

    private String remark;

}
