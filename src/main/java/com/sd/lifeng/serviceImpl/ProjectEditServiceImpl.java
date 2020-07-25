package com.sd.lifeng.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.dao.ProjectDAO;
import com.sd.lifeng.enums.ProjectReturnEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectEditService;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.util.JSONArraySortUtil;
import com.sd.lifeng.vo.project.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.Collator;
import java.util.*;

@Service
public class ProjectEditServiceImpl implements IProjectEditService {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private IProjectManageService projectManageService;
    /*
   查询单位-分部方法，并标注已存在的单位分部
   */
    public JSONObject queryEditProject(String userId){
        JSONObject result = new JSONObject();
        //先查用户是否为管理员
        Boolean flag = projectDAO.checkAdmin(userId);
        //再查可编辑项目集
        List<Map<String, Object>> list = projectDAO.queryEditProject(userId,flag);

        JSONArray array = new JSONArray();
        if(list == null || list.size() == 0){
            result.put("data",new JSONArray());
        }else{
            for(int i=0; i<list.size(); i++){
                JSONObject projectDetail = new JSONObject();
                projectDetail.put("id",list.get(i).get("id"));
                projectDetail.put("projecthash",list.get(i).get("projecthash"));
                projectDetail.put("project_name",list.get(i).get("project_name"));
                projectDetail.put("create_user",list.get(i).get("create_user"));
                projectDetail.put("role_id",list.get(i).get("role_id"));
                projectDetail.put("rolename",list.get(i).get("rolename"));
                projectDetail.put("createdate",list.get(i).get("createdate"));
                projectDetail.put("project_addr",list.get(i).get("project_addr"));
                projectDetail.put("status",list.get(i).get("status"));
                array.add(projectDetail);
            }
            result.put("data",array);
        }
        result.put("code","0");
        result.put("msg","success");

        return result;
    }

    /*
   编辑在途项目详情
   */
    public JSONObject editProjectDetail(EditProjectDetailVO editProjectDetailVO){
        JSONObject result = new JSONObject();
        //判断项目名称是否重复
        if(projectDAO.getRecordByProjectNameAndRoleId(editProjectDetailVO.getProjectName(),editProjectDetailVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.REPEAT_CHECK_ERROR);
        }
        int rows = projectDAO.updateProjectDetail(editProjectDetailVO);
        if(rows>0){
            result.put("code","0");
            result.put("msg","success");
            JSONObject projecthash = new JSONObject();
            projecthash.put("projectHash",editProjectDetailVO.getProjectHash());
            result.put("data",projecthash);
        }else{
            throw new LiFengException(ProjectReturnEnum.PROJECT_DETAIL_UPDATE_ERROR);
        }
        return result;
    }

    /*
   查询项目已选择的静态资源
   */
    public JSONObject queryProjectSource(String projectHash){
        JSONObject result = new JSONObject();
        List<Map<String, Object>> list = projectDAO.querySourceforProject(projectHash);
        if(list == null || list.size() == 0){
            result.put("data", new JSONObject());
        }else{
            JSONArray array = new JSONArray();
            for(int i=0;i<list.size();i++){
                JSONObject source = new JSONObject();
                source.put("source_type",list.get(i).get("source_type"));
                array.add(source);
            }
            result.put("data",array);
        }
        result.put("code","0");
        result.put("msg","success");
        return result;
    }

    /*
    编辑静态资源方法
     */
    public JSONObject editProjectSource(ProjectSourceVO projectSourceVO){
        JSONObject result = new JSONObject();
        int res;
        //先清空原有静态资源
        projectDAO.deleteSourceforProject(projectSourceVO.getProjectHash());
        //确认已清空
        res = projectDAO.querySourceforProject(projectSourceVO.getProjectHash()).size();
        //返回原项目串码
        JSONObject projectHash = new JSONObject();
        projectHash.put("projectHash",projectSourceVO.getProjectHash());
        result.put("data",projectHash);

        if(res == 0){
            //清空成功
            try{
                res = projectDAO.addProjectSourceBatch(projectSourceVO.getProjectHash(),projectSourceVO.getSourceList());
                //插入成功
                logger.info("成功更新"+res+"条资源");
                result.put("code","0");
                result.put("msg","成功更新"+res+"条资源");

            }catch (Exception e){
                logger.error(e.getMessage());
                //插入异常
                throw new LiFengException(ProjectReturnEnum.INSERT_SQL_EXCEPTION, e.getMessage());
            }
        }else{
            //有残余数据，不得更新
            throw new LiFengException(ProjectReturnEnum.PROJECT_SOURCE_UPDATE_ERROR);
        }

        return result;
    }

    /*
   查询项目已选择的时间线资源
   */
    public JSONObject queryProjectTimeline(String projectHash){
        JSONObject result = new JSONObject();
        List<Map<String, Object>> list = projectDAO.queryTimelineforProject(projectHash);
        if(list == null || list.size() == 0){
            result.put("data", new JSONObject());
        }else{
            JSONArray array = new JSONArray();
            for(int i=0;i<list.size();i++){
                JSONObject source = new JSONObject();
                source.put("timeline_type",list.get(i).get("timeline_type"));
                array.add(source);
            }
            result.put("data",array);
        }
        result.put("code","0");
        result.put("msg","success");
        return result;
    }

    /*
    编辑时间线资源方法
     */
    public JSONObject editProjectTimeline(ProjectTimelineVO projectTimelineVO){
        JSONObject result = new JSONObject();
        int res;
        //先清空原有时间线资源
        projectDAO.deleteTimelineforProject(projectTimelineVO.getProjectHash());
        //确认已清空
        res = projectDAO.queryTimelineforProject(projectTimelineVO.getProjectHash()).size();
        //返回原项目串码
        JSONObject projectHash = new JSONObject();
        projectHash.put("projectHash",projectTimelineVO.getProjectHash());
        result.put("data",projectHash);

        if(res == 0){
            //清空成功
            try{
                res = projectDAO.addProjectTimelineBatch(projectTimelineVO.getProjectHash(),projectTimelineVO.getTimelineList());
                //插入成功
                logger.info("成功更新"+res+"条资源");
                result.put("code","0");
                result.put("msg","成功更新"+res+"条资源");

            }catch (Exception e){
                logger.error(e.getMessage());
                //插入异常
                throw new LiFengException(ProjectReturnEnum.INSERT_SQL_EXCEPTION, e.getMessage());
            }
        }else{
            //有残余数据，不得更新
            throw new LiFengException(ProjectReturnEnum.PROJECT_TIMELINE_UPDATE_ERROR);
        }

        return result;
    }

    /*
   查询单位-分部字典,并标注在途项目现有的分部
   */
    public JSONObject queryUnitPart(String projectHash){
        JSONObject result = new JSONObject();
        JSONArray finalArray;
        //先获取单位分部字典集
        JSONArray unitPartDic = projectManageService.queryUnitPart().getJSONObject("data").getJSONArray("unit_part");
        //将字典的每个分部都设置check=0
        JSONArray addCheckArray = new JSONArray();
        //操作unitPartDic转化为addCheckArray
        //生成唯一id
        int serno = 0;
        int unit_id = 0;
        for(int i = 0; i< unitPartDic.size(); i++){
            JSONObject upObject = new JSONObject();
            serno++;
            unit_id = serno;//存储unit_id
            upObject.put("id",serno);
            upObject.put("unit_name",unitPartDic.getJSONObject(i).getString("unit_name"));

            JSONArray array = new JSONArray();
            JSONArray partList = unitPartDic.getJSONObject(i).getJSONArray("children");
            for(int j = 0; j<partList.size(); j++){
                JSONObject object = new JSONObject();
                serno++;
                object.put("id",serno);
                object.put("part_name",partList.getJSONObject(j).get("part_name"));
                object.put("unit_id",unit_id);
                object.put("check",0);
                array.add(object);
            }
            upObject.put("children",array);
            addCheckArray.add(upObject);
        }
        logger.info("addCheckArray:"+addCheckArray);

        //再获取在途项目的单位分部集
        JSONArray projectUnitPartDic = new JSONArray();
        //先查在途项目的所有单位
        List<Map<String, Object>> list = projectDAO.queryUnitforProject(projectHash);

        if(list == null || list.size() == 0){
            //在途项目未配置单位，必然也没有分部，直接将addCheckArray赋给finalArray
            finalArray = addCheckArray;
        }else{
            //再查在途项目每个单位的所有分部
            //生成唯一id
            serno = 0;
            for(int i = 0; i<list.size(); i++){
                JSONObject upObject = new JSONObject();
                serno++;
                unit_id = serno;//存储unit_id
                upObject.put("id",serno);
                upObject.put("unit_name",list.get(i).get("unit_name"));

                JSONArray array = new JSONArray();
                List<Map<String, Object>> partList = projectDAO.queryPartforProject(projectHash, (String) list.get(i).get("unit_name"));
                for(int j = 0; j<partList.size(); j++){
                    JSONObject object = new JSONObject();
                    serno++;
                    object.put("id",serno);
                    object.put("part_name",partList.get(j).get("part_name"));
                    object.put("unit_id",unit_id);
                    array.add(object);
                }
                upObject.put("children",array);
                projectUnitPartDic.add(upObject);
            }
            logger.info("projectUnitPartDic:"+projectUnitPartDic);
            //对比遍历projectUnitPartDic和addCheckArray，并将addCheckArray转化为finalArray
            String unit_name;
            String part_name;
            for(int projectUnit = 0; projectUnit<projectUnitPartDic.size(); projectUnit++){
                unit_name = projectUnitPartDic.getJSONObject(projectUnit).getString("unit_name");
                for(int dicUnit = 0; dicUnit<addCheckArray.size(); dicUnit++){
                    if(unit_name.equals(addCheckArray.getJSONObject(dicUnit).getString("unit_name"))){
                        //匹配到了已选择的unit，开始处理part
                        JSONArray pChildren = projectUnitPartDic.getJSONObject(projectUnit).getJSONArray("children");
                        for(int projectPart = 0; projectPart<pChildren.size(); projectPart++){
                            part_name = pChildren.getJSONObject(projectPart).getString("part_name");
                            JSONArray dChildren = addCheckArray.getJSONObject(dicUnit).getJSONArray("children");
                            for(int dicPart = 0; dicPart<dChildren.size(); dicPart++){
                                if(part_name.equals(dChildren.getJSONObject(dicPart).getString("part_name"))){
                                    //匹配到了已选择的part,调整check=1
                                    addCheckArray.getJSONObject(dicUnit).getJSONArray("children").getJSONObject(dicPart).put("check",1);
                                }
                            }
                        }
                    }
                }
            }
            logger.info("addCheckArray:"+addCheckArray);
            finalArray = addCheckArray;
        }

        JSONObject finalObject = new JSONObject();
        finalObject.put("project_unit_part",finalArray);

        result.put("code","0");
        result.put("msg","success");
        result.put("data",finalObject);
        return result;
    }

    /*
    编辑工程单位-分部方法
   */
    public JSONObject editProjectUnitPart(ProjectUnitPartVO projectUnitPartVO){
        JSONObject result = new JSONObject();
        int res = 0;
        //返回原项目串码
        JSONObject projectHash = new JSONObject();
        projectHash.put("projectHash",projectUnitPartVO.getProjectHash());
        //先清空原有单位分部
        projectDAO.deleteUnitPartforProject(projectUnitPartVO.getProjectHash());
        //确认已清空
        res = projectDAO.queryUnitforProject(projectUnitPartVO.getProjectHash()).size();

        if(res == 0){
            try{
                res = projectDAO.addProjectUnitPartBatch(projectUnitPartVO.getProjectHash(),projectUnitPartVO.getUnit_part());
                //插入成功，比对分部，清除多余分部的单元
                //1、查询该项目pro_project_unit_part中的所有分部
                List<Map<String, Object>> uplist = projectDAO.queryAllPartforProject(projectUnitPartVO.getProjectHash());
                //2、查询该项目pro_project_cent_list中的所有分部
                List<Map<String, Object>> clist = projectDAO.queryAllPartInCentforProject(projectUnitPartVO.getProjectHash());
                //3、筛选出pro_project_cent_list中存在，但是pro_project_unit_part中不存在的分部
                List<String> reslist = new ArrayList<>();
                String part_name;
                Boolean flag;
                for (int i=0; i<clist.size(); i++){
                    part_name = (String)clist.get(i).get("part_name");
                    flag = true;
                    for (int j=0; j<uplist.size(); j++){
                        if(part_name.equals(uplist.get(j).get("part_name"))){
                            //匹配到了分部
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        reslist.add(part_name);
                    }
                }

                if (reslist == null || reslist.size() == 0){
                    //无需清除
                    logger.info("无需清除");
                }else{
                    logger.info("多余的分部:"+reslist);
                    //4、清除pro_project_cent_list中多余的分部
                    int rows = projectDAO.deleteSurplusProjectCentBatch(projectUnitPartVO.getProjectHash(),reslist);
                    if(rows == reslist.size()){
                        logger.info("清除成功");
                    }else{
                        logger.info("！！！警告：应清除"+reslist.size()+"条数据，但是实际清除了"+rows+"条数据");
                    }
                }

                logger.info("成功更新"+res+"条单位-分部");
                result.put("code","0");
                result.put("msg","成功更新"+res+"条单位-分部");
            }catch (SQLException e){
                logger.error(e.getMessage());
                //清除pro_project_cent_list中多余的分部异常
                throw new LiFengException(ProjectReturnEnum.PROJECT_CENT_PREDO_ERROR);
            }catch (Exception e){
                logger.error(e.getMessage());
                //插入异常
                throw new LiFengException(ProjectReturnEnum.INSERT_SQL_EXCEPTION, e.getMessage());
            }
        }else{
            //有残余数据，不得更新
            throw new LiFengException(ProjectReturnEnum.PROJECT_UNIT_PART_UPDATE_ERROR);
        }
        result.put("data",projectHash);
        return result;
    }

    /*
   查询项目已选择的单元
   */
    public JSONObject queryProjectCent(String projectHash){
        JSONObject result = new JSONObject();
        //先查单元表中该项目的所有分部
        List<Map<String, Object>> partlist = projectDAO.queryAllPartInCentforProject(projectHash);
        JSONArray finalarray = new JSONArray();
        if(partlist == null || partlist.size() == 0){
            //没有记录直接返回所有分部
            partlist = projectDAO.queryProjectPartList(projectHash);
            for(int i=0; i<partlist.size(); i++){
                JSONObject object = new JSONObject();
                object.put("id",i+1);
                object.put("part_name",partlist.get(i).get("part_name"));
                object.put("children",new JSONArray());
                finalarray.add(object);
            }
        }else{
            //有记录，先处理有单元的分部
            for(int i=0;i<partlist.size();i++){
                JSONObject object = new JSONObject();
                object.put("id",i+1);
                object.put("part_name",partlist.get(i).get("part_name"));

                //再查分部下的单元
                List<Map<String, Object>> centlist = projectDAO.queryCentInPartforProject(projectHash,(String) partlist.get(i).get("part_name"));
                JSONArray centArray = new JSONArray();
                for(int j=0;j<centlist.size();j++){
                    JSONObject centObject = new JSONObject();
                    centObject.put("id",j+1);
                    centObject.put("projecthash",centlist.get(j).get("projecthash"));
                    centObject.put("part_name",centlist.get(j).get("part_name"));
                    centObject.put("cent_name",centlist.get(j).get("cent_name"));
                    centObject.put("centhash",centlist.get(j).get("centhash"));
                    centObject.put("cent_type",centlist.get(j).get("cent_type"));
                    centArray.add(centObject);
                }
                object.put("children",centArray);

                finalarray.add(object);
            }

            //再处理没有单元的分部
            //查回来所有的分部
            partlist = projectDAO.queryProjectPartList(projectHash);
            String part_name;
            Boolean flag;
            //遍历分部
            for(int i=0; i<partlist.size(); i++){
                part_name = (String) partlist.get(i).get("part_name");
                //默认分部需要加入最终集合
                flag = true;
                //遍历已完成的集合
                for(int j=0; j<finalarray.size(); j++){
                    if(part_name.equals(finalarray.getJSONObject(j).getString("part_name"))){
                        //找到了相同的分部，设置标识为false
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    //标识为真，则说明需要加入集合
                    JSONObject object = new JSONObject();
                    object.put("id",i+1);
                    object.put("part_name",partlist.get(i).get("part_name"));
                    object.put("children",new JSONArray());
                    finalarray.add(object);
                }
            }
        }

        JSONArraySortUtil sort = new JSONArraySortUtil();
        JSONArray res = sort.jsonArraySort(finalarray,"part_name");

        result.put("data",res);
        result.put("code","0");
        result.put("msg","success");
        return result;
    }

    /*
   单条删除已选择的项目单元
   */
    public JSONObject deleteProjectCent(String centHash){
        JSONObject result = new JSONObject();
        int i = projectDAO.deleteCentforProject(centHash);
        if(i == 1){
            result.put("code","0");
            result.put("msg","success");
        }else{
            throw new LiFengException(ProjectReturnEnum.CENT_DELETE_ERROR);
        }
        return result;
    }

    /*
   单项增加项目单元
   */
    public JSONObject addProjectCent(ProjectAddCentVO projectAddCentVO){
        JSONObject result = new JSONObject();
        int res = 0;

        try{
            res = projectDAO.addCentforProjectBatch(projectAddCentVO.getProjectHash(),projectAddCentVO.getCent());
            //新增成功
            logger.info("成功新增"+res+"条分部-单元");
            result.put("code","0");
            result.put("msg","成功新增"+res+"条分部-单元");
            //返回原项目串码
            JSONObject projectHash = new JSONObject();
            projectHash.put("projectHash",projectAddCentVO.getProjectHash());

            result.put("data",projectHash);
        }catch (Exception e){
            logger.error(e.getMessage());
            //插入异常
            throw new LiFengException(ProjectReturnEnum.INSERT_SQL_EXCEPTION, e.getMessage());
        }

        return result;
    }

}
