package com.sd.lifeng.vo.project;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteCentVO {
    @NotEmpty(message = "单元唯一串码不得为空")
    private String centHash;

    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;
}
