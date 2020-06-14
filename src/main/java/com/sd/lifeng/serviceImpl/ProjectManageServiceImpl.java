package com.sd.lifeng.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.dao.ProjectDAO;
import com.sd.lifeng.domain.ProjectDO;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.util.DateUtil;
import com.sd.lifeng.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

    /*
   新增项目方法
    */
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

    /*
    查询静态资源方法
     */
    public JSONObject querySource(){
        JSONObject result = new JSONObject();
        //查询表
        List<Map<String, Object>> list = projectDAO.querySource();

        if(list == null || list.size() == 0){
            //静态资源为空
            result.put("code","1002");
            result.put("msg","系统内尚未配置静态资源");
        }else{
            JSONArray array = new JSONArray();
            for(int i = 0; i<list.size(); i++){
                JSONObject object = new JSONObject();
                object.put("id",i+1);
                object.put("source_type",list.get(i).get("source_type"));
                object.put("source_name",list.get(i).get("source_name"));
                array.add(object);
            }
            JSONObject sourceList = new JSONObject();
            sourceList.put("sourceList",array);

            result.put("code","0");
            result.put("msg","success");
            result.put("data",sourceList);
        }
        return result;
    }

    /*
    新增静态资源方法
     */
    public JSONObject addProjectSource(ProjectSourceVO projectSourceVO){
        JSONObject result = new JSONObject();
        int res = 0;

        try{
            res = projectDAO.addProjectSourceBatch(projectSourceVO.getProjectHash(),projectSourceVO.getSourceList());
            //插入成功
            logger.info("成功插入"+res+"条资源");
            result.put("code","0");
            result.put("msg","成功插入"+res+"条资源");
            //返回原项目串码
            JSONObject projectHash = new JSONObject();
            projectHash.put("projectHash",projectSourceVO.getProjectHash());

            result.put("data",projectHash);
        }catch (Exception e){
            logger.error(e.getMessage());
            //插入异常
            result.put("code","1003");
            result.put("msg",e.getMessage());
        }
        return result;
    }

    /*
    查询时间线资源方法
     */
    public JSONObject queryTimeline(){
        JSONObject result = new JSONObject();
        //查询表
        List<Map<String, Object>> list = projectDAO.queryTimeline();

        if(list == null || list.size() == 0){
            //静态资源为空
            result.put("code","1004");
            result.put("msg","系统内尚未配置时间线资源");
        }else{
            JSONArray array = new JSONArray();
            for(int i = 0; i<list.size(); i++){
                JSONObject object = new JSONObject();
                object.put("id",i+1);
                object.put("timeline_type",list.get(i).get("timeline_type"));
                object.put("timeline_name",list.get(i).get("timeline_name"));
                array.add(object);
            }
            JSONObject timelineList = new JSONObject();
            timelineList.put("timelineList",array);

            result.put("code","0");
            result.put("msg","success");
            result.put("data",timelineList);
        }
        return result;
    }

    /*
    新增时间线资源方法
     */
    public JSONObject addProjectTimeline(ProjectTimelineVO projectTimelineVO){
        JSONObject result = new JSONObject();
        int res = 0;

        try{
            res = projectDAO.addProjectTimelineBatch(projectTimelineVO.getProjectHash(),projectTimelineVO.getTimelineList());
            //插入成功
            logger.info("成功插入"+res+"条时间线资源");
            result.put("code","0");
            result.put("msg","成功插入"+res+"条时间线资源");
            //返回原项目串码
            JSONObject projectHash = new JSONObject();
            projectHash.put("projectHash",projectTimelineVO.getProjectHash());

            result.put("data",projectHash);
        }catch (Exception e){
            logger.error(e.getMessage());
            //插入异常
            result.put("code","1005");
            result.put("msg",e.getMessage());
        }
        return result;
    }

    /*
   查询单位-分部方法
   */
    public JSONObject queryUnitPart(){
        JSONObject result = new JSONObject();
        //先查所有的单位
        List<Map<String, Object>> list = projectDAO.queryUnit();

        if(list == null || list.size() == 0){
            //未配置单位
            result.put("code","1006");
            result.put("msg","单位资源未配置");
        }else{
            //再查每个单位的所有分部
            JSONArray finalArray = new JSONArray();
            for(int i = 0; i<list.size(); i++){
                JSONObject upObject = new JSONObject();
                JSONArray array = new JSONArray();
                List<Map<String, Object>> partList = projectDAO.queryPart((String) list.get(i).get("unit_name"));
                addPartName(partList, array,i+1);
                upObject.put("id",i+1);
                upObject.put("unit_name",list.get(i).get("unit_name"));
                upObject.put("children",array);
                finalArray.add(upObject);
            }

            JSONObject finalObject = new JSONObject();
            finalObject.put("unit_part",finalArray);

            result.put("code","0");
            result.put("msg","success");
            result.put("data",finalObject);
        }

        return result;
    }

    /*
    新增工程单位-分部方法
   */
    public JSONObject addProjectUnitPart(ProjectUnitPartVO projectUnitPartVO){
        JSONObject result = new JSONObject();
        int res = 0;

        try{
            res = projectDAO.addProjectUnitPartBatch(projectUnitPartVO.getProjectHash(),projectUnitPartVO.getUnit_part());
            //插入成功
            logger.info("成功插入"+res+"条单位-分部");
            result.put("code","0");
            result.put("msg","成功插入"+res+"条单位-分部");
            //返回原项目串码
            JSONObject projectHash = new JSONObject();
            projectHash.put("projectHash",projectUnitPartVO.getProjectHash());

            result.put("data",projectHash);
        }catch (Exception e){
            logger.error(e.getMessage());
            //插入异常
            result.put("code","1007");
            result.put("msg",e.getMessage());
        }
        return result;
    }

    /*
    查询单元方法
     */
    public JSONObject queryCent(){
        JSONObject result = new JSONObject();
        //查询表
        List<Map<String, Object>> list = projectDAO.queryCent();

        if(list == null || list.size() == 0){
            //静态资源为空
            result.put("code","1008");
            result.put("msg","系统内尚未配置单元模板");
        }else{
            JSONArray array = new JSONArray();
            for(int i = 0; i<list.size(); i++){
                JSONObject object = new JSONObject();
                object.put("id",i+1);
                object.put("cent_name",list.get(i).get("cent_name"));
                object.put("cent_type",list.get(i).get("cent_type"));

                if("2".equals(list.get(i).get("cent_type"))){
                    object.put("measurement",list.get(i).get("measurement"));
                }else{
                    object.put("measurement","");
                }
                array.add(object);
            }
            JSONObject centList = new JSONObject();
            centList.put("centList",array);

            result.put("code","0");
            result.put("msg","success");
            result.put("data",centList);
        }
        return result;
    }

    /*
    查询某项目所有分部
     */
    public JSONObject queryProjectPartList(String projectHash){
        JSONObject result = new JSONObject();
        //查询表
        List<Map<String, Object>> list = projectDAO.queryProjectPartList(projectHash);

        if(list == null || list.size() == 0){
            //单位-分部为空
            result.put("code","1011");
            result.put("msg","查询异常，该项目未添加任何单位-分部");
        }else{
            JSONArray array = new JSONArray();
            addPartName(list, array,0);
            JSONObject projectPartList = new JSONObject();
            projectPartList.put("projectPartList",array);
            projectPartList.put("projectHash",projectHash);

            result.put("code","0");
            result.put("msg","success");
            result.put("data",projectPartList);
        }
        return result;
    }

    /*
   新增工程分部-单元方法
    */
    public JSONObject addProjectPartCent(ProjectPartCentVO projectPartCentVO){
        JSONObject result = new JSONObject();
        int res = 0;

        try{
            res = projectDAO.addProjectPartCentBatch(projectPartCentVO.getProjectHash(),projectPartCentVO.getPart_cent());
            //插入成功
            logger.info("成功插入"+res+"条分部-单元");
            result.put("code","0");
            result.put("msg","成功插入"+res+"条分部-单元");
            //返回原项目串码
            JSONObject projectHash = new JSONObject();
            projectHash.put("projectHash",projectPartCentVO.getProjectHash());

            result.put("data",projectHash);
        }catch (Exception e){
            logger.error(e.getMessage());
            //插入异常
            result.put("code","1012");
            result.put("msg",e.getMessage());
        }
        return result;
    }

    /*
    封装分部列表公共方法
     */
    private void addPartName(List<Map<String, Object>> list, JSONArray array,int unit_id) {
        for(int i = 0; i<list.size(); i++){
            JSONObject object = new JSONObject();
            object.put("id",i+1);
            object.put("part_name",list.get(i).get("part_name"));
            if(unit_id != 0) {
                object.put("unit_id",unit_id);
            }
            array.add(object);
        }
    }
}
