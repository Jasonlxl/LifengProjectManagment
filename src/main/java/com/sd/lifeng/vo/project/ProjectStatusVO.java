package com.sd.lifeng.vo.project;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProjectStatusVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotNull(message = "状态不得为空")
    private int status;
}
