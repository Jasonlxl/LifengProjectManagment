package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.NewProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/lifeng/projectctl")
@CrossOrigin
public class ProjectManageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProjectManageService projectManageService;

    /*
    新增项目接口
     */
    @ResponseBody
    @PostMapping("/addnewpro")
    public JSONObject register(@RequestBody @Valid NewProjectVO newProjectVO, BindingResult bindingResult){
        logger.info("addnewpro send into msg :"+newProjectVO);
        JSONObject response = new JSONObject();

        if(bindingResult.hasErrors()){
            logger.error("【新增工程】参数不正确，newProjectVO={}",newProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //先搜索是否有同一个甲方的同名项目
        if(projectManageService.repeatCheck(newProjectVO.getProjectName(),newProjectVO.getRoleId())){
            response.put("code","1001");
            response.put("msg","已存在该项目，请勿重复添加");
            logger.info("response :"+response);
            return response;
        }

        //尝试新增项目
        response = projectManageService.addNewProject(newProjectVO);

        logger.info("response :"+response);
        return response;
    }
}
