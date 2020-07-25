package com.sd.lifeng.vo.project;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryEditProjectListVO {
    @NotEmpty(message = "用户ID不得为空")
    private String userId;
}
