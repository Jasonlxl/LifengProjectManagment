package com.sd.lifeng.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.dao.ProjectDAO;
import com.sd.lifeng.domain.ProjectDO;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.util.DateUtil;
import com.sd.lifeng.vo.NewProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2020/5/18.
 */
@Service
public class ProjectManageServiceImpl implements IProjectManageService {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectDAO projectDAO;
    /*
    根据项目名称、甲方角色id判重
     */
    public boolean repeatCheck(String projectName, int roleId){
        return projectDAO.getRecordByProjectNameAndRoleId(projectName,roleId);
    }

    public JSONObject addNewProject(NewProjectVO newProjectVO){
        JSONObject result = new JSONObject();
        //封装DO数据
        ProjectDO projectDO = new ProjectDO();
        projectDO.setProjectHash( UUID.randomUUID().toString());
        projectDO.setProjectName(newProjectVO.getProjectName());
        projectDO.setCreateUser(newProjectVO.getCreateUser());
        projectDO.setRoleId(newProjectVO.getRoleId());
        projectDO.setRoleName(newProjectVO.getRoleName());
        projectDO.setProjectAddr(newProjectVO.getProjectAddr());

        int rownum = 0;
        try {
            rownum = projectDAO.addProjectRecord(projectDO);
            logger.info("new row:"+rownum);
            JSONObject data = new JSONObject();

            //封装data数据
            data.put("projectHash",projectDO.getProjectHash());

            result.put("code","0");
            result.put("msg","success");
            result.put("data",data);

            logger.info("result:"+result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","-1");
            result.put("msg",e.getMessage());

            logger.error("error:"+e.getMessage());
        }

        return result;
    }
}
