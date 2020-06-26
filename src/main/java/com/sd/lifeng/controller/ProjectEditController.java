package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.enums.ProjectReturnEnum;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectEditService;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.project.*;
import org.junit.platform.commons.util.StringUtils;
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
   查询可编辑项目列表
    */
    @ResponseBody
    @PostMapping("/queryeditproject")
    public JSONObject queryEditProject(@RequestBody @Valid QueryEditProjectListVO queryEditProjectListVO, BindingResult bindingResult){
        logger.info("【查询可编辑项目列表】:"+queryEditProjectListVO);

        if(bindingResult.hasErrors()){
            logger.error("【查询可编辑项目列表】参数不正确，queryEditProjectListVO={}",queryEditProjectListVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询可编辑项目列表
        JSONObject response = projectEditService.queryEditProject(queryEditProjectListVO.getUserId());
        logger.info("response:"+response);
        return response;
    }

    /*
    编辑在途项目详情
     */
    @ResponseBody
    @PostMapping("/editprojectdetail")

    public JSONObject editProjectDetail(@RequestBody @Valid EditProjectDetailVO editProjectDetailVO, BindingResult bindingResult){
        logger.info("【编辑在途项目详情】:");

        if(bindingResult.hasErrors()){
            logger.error("【编辑在途项目详情】参数不正确，editProjectDetailVO={}",editProjectDetailVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //编辑在途项目详情
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(editProjectDetailVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.editProjectDetail(editProjectDetailVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询项目已选择的静态资源
     */
    @ResponseBody
    @PostMapping("/queryprojectsource")
    public JSONObject queryProjectSource(@RequestBody @Valid QueryEditProjectVO queryEditProjectVO, BindingResult bindingResult){
        logger.info("【查询在途项目已选择的静态资源】:");

        if(bindingResult.hasErrors()){
            logger.error("【新增在途项目已选择的静态资源】参数不正确，queryEditProjectVO={}", queryEditProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目静态资源
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(queryEditProjectVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.queryProjectSource(queryEditProjectVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    编辑项目静态资源
     */
    @ResponseBody
    @PostMapping("/editprojectsource")

    public JSONObject editProjectSource(@RequestBody @Valid ProjectSourceVO projectSourceVO, BindingResult bindingResult){
        logger.info("【编辑工程静态资源】:"+projectSourceVO);

        if(bindingResult.hasErrors()){
            logger.error("【编辑工程静态资源】参数不正确，projectSourceVO={}",projectSourceVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //编辑静态资源
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectSourceVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.editProjectSource(projectSourceVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询项目已选择的时间线资源
     */
    @ResponseBody
    @PostMapping("/queryprojecttimeline")
    public JSONObject queryProjectTimeline(@RequestBody @Valid QueryEditProjectVO queryEditProjectVO, BindingResult bindingResult){
        logger.info("【查询在途项目已选择的时间线资源】:");

        if(bindingResult.hasErrors()){
            logger.error("【新增在途项目已选择的时间线资源】参数不正确，queryEditProjectVO={}", queryEditProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目时间线
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(queryEditProjectVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.queryProjectTimeline(queryEditProjectVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    编辑项目时间线资源
     */
    @ResponseBody
    @PostMapping("/editprojecttimeline")

    public JSONObject editProjectTimeline(@RequestBody @Valid ProjectTimelineVO projectTimelineVO, BindingResult bindingResult){
        logger.info("【编辑工程时间线资源】:"+projectTimelineVO);

        if(bindingResult.hasErrors()){
            logger.error("【编辑工程时间线资源】参数不正确，projectTimelineVO={}",projectTimelineVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //编辑时间线资源
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectTimelineVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.editProjectTimeline(projectTimelineVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询单位-分部字典,并标注在途项目现有的分部
     */
    @ResponseBody
    @PostMapping("/queryprojectunitpart")
    public JSONObject queryUnitPart(@RequestBody @Valid QueryEditProjectVO queryEditProjectVO, BindingResult bindingResult){
        logger.info("【查询在途项目单位-分部】:");

        if(bindingResult.hasErrors()){
            logger.error("【新增在途项目单位-分部】参数不正确，queryEditProjectVO={}", queryEditProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目单元-分部
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(queryEditProjectVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.queryUnitPart(queryEditProjectVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    编辑项目单位-分部
     */
    @ResponseBody
    @PostMapping("/editprojectunitpart")
    public JSONObject editProjectUintPart(@RequestBody @Valid ProjectUnitPartVO projectUnitPartVO, BindingResult bindingResult){
        logger.info("【编辑单位-分部】:"+projectUnitPartVO);

        if(bindingResult.hasErrors()){
            logger.error("【编辑单位-分部】参数不正确，projectUnitPartVO={}",projectUnitPartVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //新增单元-分部
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectUnitPartVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.editProjectUnitPart(projectUnitPartVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询在途项目单元列表
     */
    @ResponseBody
    @PostMapping("/queryprojectcent")
    public JSONObject queryProjectCent(@RequestBody @Valid QueryEditProjectVO queryEditProjectVO, BindingResult bindingResult){
        logger.info("【查询在途项目单元列表】:");

        if(bindingResult.hasErrors()){
            logger.error("【新增在途项目单元列表】参数不正确，queryEditProjectVO={}", queryEditProjectVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目单元列表
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(queryEditProjectVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.queryProjectCent(queryEditProjectVO.getProjectHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    单条删除已选择的项目单元
     */
    @ResponseBody
    @PostMapping("/deleteprojectcent")
    public JSONObject deleteProjectCent(@RequestBody @Valid DeleteCentVO deleteCentVO, BindingResult bindingResult){
        logger.info("【单条删除已选择的项目单元】:");

        if(bindingResult.hasErrors()){
            logger.error("【单条删除已选择的项目单元】参数不正确，deleteCentVO={}", deleteCentVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目单元列表
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(deleteCentVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.deleteProjectCent(deleteCentVO.getCentHash());
        logger.info("response:"+response);
        return response;
    }

    /*
    单项增加项目单元
     */
    @ResponseBody
    @PostMapping("/addprojectcent")
    public JSONObject addProjectCent(@RequestBody @Valid ProjectAddCentVO projectAddCentVO, BindingResult bindingResult){
        logger.info("【单项增加项目单元】:");

        if(bindingResult.hasErrors()){
            logger.error("【单项增加项目单元】参数不正确，projectAddCentVO={}", projectAddCentVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //查询在途项目单元列表
        JSONObject response;

        //先验证项目可否编辑
        if(projectManageService.editCheck(projectAddCentVO.getProjectHash())){
            throw new LiFengException(ProjectReturnEnum.PROJECT_CANNOT_EDIT_ERROR);
        }

        response = projectEditService.addProjectCent(projectAddCentVO);
        logger.info("response:"+response);
        return response;
    }
}
