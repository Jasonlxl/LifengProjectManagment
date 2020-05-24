package com.sd.lifeng.domain;

import lombok.Data;

@Data
public class ProjectDO {
    private Integer id;
    private String projectHash;
    private String projectName;
    private Integer createUser;
    private Integer roleId;
    private String roleName;
    private String createDate;
    private String projectAddr;

}
