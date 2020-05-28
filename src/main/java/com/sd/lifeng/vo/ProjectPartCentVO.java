package com.sd.lifeng.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
新增工程单元-分部
 */
@Data
public class ProjectPartCentVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotEmpty(message = "分部-单元不得为空")
    private JSONArray part_cent;
}
