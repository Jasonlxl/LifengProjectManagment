package com.sd.lifeng.vo.project;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
编辑工程项目详情视图
 */
@Data
public class EditProjectDetailVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotEmpty(message = "工程名不能为空")
    @Size(max=90,message="工程名称应控制在90个汉字以内")
    private String projectName;

    @NotNull(message = "甲方角色不得为空")
    private int roleId;

    @NotEmpty(message = "甲方角色名称不得为空")
    private String roleName;

    @NotEmpty(message = "项目地址不得为空")
    @Size(max=120,message="工程名称应控制在120个汉字以内")
    private String projectAddr;
}
