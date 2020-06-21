package com.sd.lifeng.vo.project;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryEditProjectVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;
}
