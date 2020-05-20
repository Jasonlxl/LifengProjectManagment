package com.sd.lifeng.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IUserCategoryService;
import org.apache.catalina.LifecycleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserCategoryService userCategoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http请求头中取出token
        String token = request.getHeader("token");

        //如果不是映射到controller方法直接放行
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod) handler;
        Method method=handlerMethod.getMethod();
        //todo 将这里的异常全部定义成LifengException   捕获这个异常进行处理

        //检查需不需要验证token
        if(method.isAnnotationPresent(VerifyToken.class)){
            VerifyToken userLoginToken = method.getAnnotation(VerifyToken.class);
            if(userLoginToken.required()){
                if(StringUtils.isEmpty(token)){
                    throw new LiFengException(ResultCodeEnum.TOKEN_MISS);
                }

                //获取token中的userId
                String userId;
                try {
                    userId= JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException e){
                    throw new LiFengException(ResultCodeEnum.TOKEN_ILLEGAL);
                }
                UserDO userDO = userCategoryService.findUserById(Integer.parseInt(userId));
                if(userDO == null){
                    throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
                }

                //验证token
                JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256((userDO.getPasswd()))).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e){
                    throw new LiFengException(ResultCodeEnum.SERVER_EXCEPTION.getCode(),"jwt解析异常");
                }
                return true;
            }
        }

        return true;
    }


}
