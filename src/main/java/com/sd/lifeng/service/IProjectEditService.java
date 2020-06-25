package com.sd.lifeng.service;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.vo.project.*;

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
   查询项目已选择的静态资源
   */
    public JSONObject queryProjectSource(String projectHash);

    /*
    编辑工程静态资源方法
     */
    public JSONObject editProjectSource(ProjectSourceVO projectSourceVO);

    /*
   查询项目已选择的时间线资源
   */
    public JSONObject queryProjectTimeline(String projectHash);

    /*
    编辑工程时间线资源方法
     */
    public JSONObject editProjectTimeline(ProjectTimelineVO projectTimelineVO);

    /*
   查询单位-分部方法，并标注已存在的单位分部
   */
    public JSONObject queryUnitPart(String projectHash);

    /*
   编辑工程单位-分部方法
    */
    public JSONObject editProjectUnitPart(ProjectUnitPartVO projectUnitPartVO);

    /*
   查询分部-单元列表方法
   */
    public JSONObject queryProjectCent(String projectHash);

    /*
   单条删除已选择的项目单元
   */
    public JSONObject deleteProjectCent(String centHash);

    /*
   单项增加项目单元
   */
    public JSONObject addProjectCent(ProjectAddCentVO projectAddCentVO);
}
