package com.sd.lifeng.service;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.vo.NewProjectVO;
import com.sd.lifeng.vo.ProjectSourceVO;

public interface IProjectManageService {
    /*
    根据项目名称、甲方角色id判重
     */
    public boolean repeatCheck(String projectName, int roleId);

    /*
    新增项目方法
     */
    public JSONObject addNewProject(NewProjectVO newProjectVO);

    /*
    查询静态资源方法
     */
    public JSONObject querySource();

    /*
    新增静态资源方法
     */
    public JSONObject addProjectSource(ProjectSourceVO projectSourceVO);
}
