package com.sd.lifeng.vo.project;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProjectAddCentVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotEmpty(message = "单元对象不得为空")
    private JSONObject cent;
}
