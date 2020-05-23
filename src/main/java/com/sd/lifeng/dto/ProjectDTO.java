package com.sd.lifeng.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Integer id;
    private String projectHash;
    private String projectName;
    private Integer createUser;
    private Integer roleId;
    private String roleName;
    private String createDate;
    private String projectAddr;
}
