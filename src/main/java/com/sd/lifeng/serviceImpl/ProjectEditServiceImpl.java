package com.sd.lifeng.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.dao.ProjectDAO;
import com.sd.lifeng.service.IProjectEditService;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.project.EditProjectDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        int rows = projectDAO.updateProjectDetail(editProjectDetailVO);
        if(rows>0){
            result.put("code","0");
            result.put("msg","success");
            JSONObject projecthash = new JSONObject();
            projecthash.put("projectHash",editProjectDetailVO.getProjectHash());
            result.put("data",projecthash);
        }else{
            result.put("code","1015");
            result.put("msg","项目更新失败");
            result.put("data",new JSONObject());
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

}
