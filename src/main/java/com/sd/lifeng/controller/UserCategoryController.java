package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.service.IUserCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lifeng/userctl")
@CrossOrigin
public class UserCategoryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserCategoryService userCat;

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody JSONObject req){
        logger.info("register send into msg :"+req);
        JSONObject response = new JSONObject();

        //1、验证JWT

        //2、验证密码
        try{
            if(userCat.passwdCheck(req.getString("passwd"))){
                response.put("return_code","0");
                response.put("return_msg","success");
            }else{
                response.put("return_code","1");
                response.put("return_msg","passwd wrong");
            }

        }catch(Exception e){
            e.printStackTrace();
            logger.error("error:"+e.getMessage());
            response.put("return_code","-1");
            response.put("return_msg",e.getMessage());
        }

        logger.info("response:"+response);
        return response.toString();
    }
}
