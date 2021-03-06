package com.sd.lifeng.vo.project;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
新增工程单元-分部
 */
@Data
public class ProjectUnitPartVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotEmpty(message = "单位-分部不得为空")
    private JSONArray unit_part;
}
