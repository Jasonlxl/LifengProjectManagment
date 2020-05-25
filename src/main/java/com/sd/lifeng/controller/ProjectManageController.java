package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IProjectManageService;
import com.sd.lifeng.vo.NewProjectVO;
import com.sd.lifeng.vo.ProjectSourceVO;
import com.sd.lifeng.vo.ProjectTimelineVO;
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

    /*
    查询静态资源字典
     */
    @ResponseBody
    @PostMapping("/querysource")
    public JSONObject querySource(@RequestBody String req){
        logger.info("【查询静态资源字典】:"+req);
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
        JSONObject response = projectManageService.addProjectSource(projectSourceVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询时间线资源字典
     */
    @ResponseBody
    @PostMapping("/querytimeline")
    public JSONObject queryTimeline(@RequestBody String req){
        logger.info("【查询时间线字典】:"+req);
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
        JSONObject response = projectManageService.addProjectTimeline(projectTimelineVO);
        logger.info("response:"+response);
        return response;
    }

    /*
    查询单元-分部字典
     */
    @ResponseBody
    @PostMapping("/queryunitpart")
    public JSONObject queryUnitPart(@RequestBody String req){
        logger.info("【查询单元-分部字典】:"+req);
        //查询单元-分部
        JSONObject response = projectManageService.queryUnitPart();
        logger.info("response:"+response);
        return response;
    }
}
