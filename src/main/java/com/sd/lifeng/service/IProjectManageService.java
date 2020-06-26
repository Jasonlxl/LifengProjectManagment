package com.sd.lifeng.service;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.vo.project.*;

public interface IProjectManageService {
    /*
    根据项目名称、甲方角色id判重
     */
    public boolean repeatCheck(String projectName, int roleId);

    /*
    根据项目串号判断是否可以编辑
     */
    public boolean editCheck(String projectHash);

    /*
    新增项目方法
     */
    public JSONObject addNewProject(NewProjectVO newProjectVO);

    /*
    查询静态资源方法
     */
    public JSONObject querySource();

    /*
    新增工程静态资源方法
     */
    public JSONObject addProjectSource(ProjectSourceVO projectSourceVO);

    /*
    查询时间线资源方法
    */
    public JSONObject queryTimeline();

    /*
    新增工程时间线资源方法
     */
    public JSONObject addProjectTimeline(ProjectTimelineVO projectTimelineVO);

    /*
   查询单位-分部方法
   */
    public JSONObject queryUnitPart();

    /*
   新增工程单位-分部方法
    */
    public JSONObject addProjectUnitPart(ProjectUnitPartVO projectUnitPartVO);

    /*
   查询单元字典方法
   */
    public JSONObject queryCent();

    /*
   查询某项目所有分部
   */
    public JSONObject queryProjectPartList(String projectHash);

    /*
    新增工程分部-单元方法
   */
    public JSONObject addProjectPartCent(ProjectPartCentVO projectPartCentVO);

    /*
    操作状态方法
     */
    public JSONObject changeStatus(ProjectStatusVO projectStatusVO);
}
