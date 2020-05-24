package com.sd.lifeng.domain;

import lombok.Data;

@Data
public class UserDO {
    private Integer id;
    private String telno;
    private String passwd;
    private String realname;
    private String salt;
    private Integer userTypeId;
    private Integer roleid;
    private String createdate;
    private String remark;
    private int systemManager;
}
