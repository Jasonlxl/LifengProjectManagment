package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectEditService;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.project.ProjectEditQueryUnitPartVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/lifeng/projectedit")
@CrossOrigin
public class ProjectEditController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProjectEditService projectEditService;

    @Autowired
    private IProjectManageService projectManageService;

    /*
    查询单位-分部字典,并标注在途项目现有的分部
     */
    @ResponseBody
    @PostMapping("/queryprojectunitpart")
    public JSONObject queryUnitPart(@RequestBody @Valid ProjectEditQueryUnitPartVO projectEditQueryUnitPartVO, BindingResult bindingResult){
        logger.info("【查询在途项目单位-分部】:");

        if(bindingResult.hasErrors()){
            logger.error("【新增在途项目单位-分部】参数不正确，projectEditQueryUnitPartVO={}",projectEditQueryUnitPartVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目单元-分部
        JSONObject response = new JSONObject();

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectEditQueryUnitPartVO.getProjectHash())){
            response.put("code","1013");
            response.put("msg","该项目已启动或已竣工");
            logger.info("response :"+response);
            return response;
        }

        response = projectEditService.queryUnitPart(projectEditQueryUnitPartVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }
}
