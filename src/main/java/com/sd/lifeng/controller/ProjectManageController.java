package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.enums.ProjectReturnEnum;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.project.*;
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
    @VerifyToken
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
            throw new LiFengException(ProjectReturnEnum.REPEAT_CHECK_ERROR);
        }

        //尝试新增项目
        response = projectManageService.addNewProject(newProjectVO);

        logger.info("response :"+response);
        return response;
    }

    /*
    查询静态资源字典
     */
    @ResponseBody
    @PostMapping("/querysource")
    public JSONObject querySource(){
        logger.info("【查询静态资源字典】:");
        //查询静态资源
        JSONObject response = projectManageService.querySource();
        logger.info("response:"+response);
        return response;
    }

    /*
    插入项目静态资源
     */
    @ResponseBody
    @PostMapping("/addprojectsource")
    public JSONObject addProjectSource(@RequestBody @Valid ProjectSourceVO projectSourceVO, BindingResult bindingResult){
        logger.info("【新增工程静态资源】:"+projectSourceVO);

        if(bindingResult.hasErrors()){
            logger.error("【新增工程静态资源】参数不正确，projectSourceVO={}",projectSourceVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //新增静态资源
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectSourceVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectManageService.addProjectSource(projectSourceVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询时间线资源字典
     */
    @ResponseBody
    @PostMapping("/querytimeline")
    public JSONObject queryTimeline(){
        logger.info("【查询时间线字典】:");
        //查询时间线资源
        JSONObject response = projectManageService.queryTimeline();
        logger.info("response:"+response);
        return response;
    }

    /*
    插入项目时间线资源
     */
    @ResponseBody
    @PostMapping("/addprojecttimeline")
    public JSONObject addProjectTimeline(@RequestBody @Valid ProjectTimelineVO projectTimelineVO, BindingResult bindingResult){
        logger.info("【新增工程时间线资源】:"+projectTimelineVO);

        if(bindingResult.hasErrors()){
            logger.error("【新增工程时间线资源】参数不正确，projectTimelineVO={}",projectTimelineVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //新增时间线资源
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectTimelineVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectManageService.addProjectTimeline(projectTimelineVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询单位-分部字典
     */
    @ResponseBody
    @PostMapping("/queryunitpart")
    public JSONObject queryUnitPart(){
        logger.info("【查询单位-分部字典】:");
        //查询单元-分部
        JSONObject response = projectManageService.queryUnitPart();
        logger.info("response:"+response);
        return response;
    }

    /*
    插入项目单位-分部
     */
    @ResponseBody
    @PostMapping("/addprojectunitpart")
    public JSONObject addProjectUintPart(@RequestBody @Valid ProjectUnitPartVO projectUnitPartVO, BindingResult bindingResult){
        logger.info("【新增单位-分部】:"+projectUnitPartVO);

        if(bindingResult.hasErrors()){
            logger.error("【新增单位-分部】参数不正确，projectUnitPartVO={}",projectUnitPartVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //新增单元-分部
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectUnitPartVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectManageService.addProjectUnitPart(projectUnitPartVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询单元字典
     */
    @ResponseBody
    @PostMapping("/querycent")
    public JSONObject queryCent(){
        logger.info("【查询单元字典】:");
        //查询单位
        JSONObject response = projectManageService.queryCent();
        logger.info("response:"+response);
        return response;
    }

    /*
    查询某项目所有分部
     */
    @ResponseBody
    @PostMapping("/queryprojectpartlist")
    public JSONObject queryProjectPartList(@RequestBody @Valid QueryEditProjectVO queryEditProjectVO, BindingResult bindingResult){
        logger.info("【查询某项目所有分部】:");

        if(bindingResult.hasErrors()){
            logger.error("【查询某项目所有分部】参数不正确，queryEditProjectVO={}", queryEditProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询某项目所有分部
        JSONObject response = projectManageService.queryProjectPartList(queryEditProjectVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    插入项目分部-单元
     */
    @ResponseBody
    @PostMapping("/addprojectpartcent")
    public JSONObject addProjectPartCent(@RequestBody @Valid ProjectPartCentVO projectPartCentVO, BindingResult bindingResult){
        logger.info("【新增分部-单元】:"+projectPartCentVO);

        if(bindingResult.hasErrors()){
            logger.error("【新增分部-单元】参数不正确，projectPartCentVO={}",projectPartCentVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //新增分部-单元
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectPartCentVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectManageService.addProjectPartCent(projectPartCentVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    项目状态操作接口
     */
    @ResponseBody
    @PostMapping("/status")
    public JSONObject status(@RequestBody @Valid ProjectStatusVO projectStatusVO, BindingResult bindingResult){
        logger.info("【操作状态】 :"+projectStatusVO);
        JSONObject response = new JSONObject();

        if(bindingResult.hasErrors()){
            logger.error("【操作状态】参数不正确，projectStatusVO={}",projectStatusVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //尝试操作状态
        response = projectManageService.changeStatus(projectStatusVO);

        logger.info("response :"+response);
        return response;
    }
}
