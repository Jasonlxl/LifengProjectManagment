package com.sd.lifeng.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.dao.ProjectDAO;
import com.sd.lifeng.service.IProjectEditService;
import com.sd.lifeng.service.IProjectManageService;
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
   查询单位-分部字典,并标注在途项目现有的分部
   */
    public JSONObject queryUnitPart(String projectHash){
        JSONObject result = new JSONObject();
        JSONArray finalArray = new JSONArray();
        //先获取单位分部字典集
        JSONArray unitPartDic = projectManageService.queryUnitPart().getJSONObject("data").getJSONArray("unit_part");

        //再获取在途项目的单位分部集
        JSONArray projectUnitPartDic = new JSONArray();
        //先查在途项目的所有单位
        List<Map<String, Object>> list = projectDAO.queryUnitforProject(projectHash);

        if(list == null || list.size() == 0){
            //在途项目未配置单位，必然也没有分部，直接将字典的每个分部都设置check=0
            //操作unitPartDic转化为finalArray

        }else{
            //再查在途项目每个单位的所有分部
            for(int i = 0; i<list.size(); i++){
                JSONObject upObject = new JSONObject();
                JSONArray array = new JSONArray();
                List<Map<String, Object>> partList = projectDAO.queryPartforProject(projectHash, (String) list.get(i).get("unit_name"));
                addPartName(partList, array,i+1);
                upObject.put("id",i+1);
                upObject.put("unit_name",list.get(i).get("unit_name"));
                upObject.put("children",array);
                projectUnitPartDic.add(upObject);
            }
            logger.info("projectUnitPartDic:"+projectUnitPartDic);
            finalArray = projectUnitPartDic;
            //对比遍历projectUnitPartDic和unitPartDic，并将unitPartDic转化为finalArray




        }

        JSONObject finalObject = new JSONObject();
        finalObject.put("project_unit_part",finalArray);

        result.put("code","0");
        result.put("msg","success");
        result.put("data",finalObject);
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
