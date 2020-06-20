package com.sd.lifeng.service;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.vo.project.EditProjectDetailVO;

public interface IProjectEditService {
    /*
   查询单位-分部方法，并标注已存在的单位分部
   */
    public JSONObject queryEditProject(String userId);

    /*
   编辑在途项目详情
   */
    public JSONObject editProjectDetail(EditProjectDetailVO editProjectDetailVO);

    /*
   查询单位-分部方法，并标注已存在的单位分部
   */
    public JSONObject queryUnitPart(String projectHash);
}
