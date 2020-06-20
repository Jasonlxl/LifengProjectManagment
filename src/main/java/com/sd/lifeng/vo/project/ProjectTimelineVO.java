package com.sd.lifeng.vo.project;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
新增工程静态资源视图
 */
@Data
public class ProjectTimelineVO {
    @NotEmpty(message = "项目串码不得为空")
    private String projectHash;

    @NotEmpty(message = "时间线资源不得为空")
    private JSONArray timelineList;
}
