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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        //检查需不需要验证token
        if(method.isAnnotationPresent(VerifyToken.class)){
            VerifyToken userLoginToken = method.getAnnotation(VerifyToken.class);
            if(userLoginToken.required()){
                if(StringUtils.isEmpty(token)){
                    throw new LiFengException(ResultCodeEnum.TOKEN_MISS);
                }

                //获取token中的userId和expireTime
                String userId;
                String expireTime;
                try {
                    userId= JWT.decode(token).getAudience().get(0);
                    expireTime= JWT.decode(token).getAudience().get(1);
                }catch (JWTDecodeException e){
                    throw new LiFengException(ResultCodeEnum.TOKEN_ILLEGAL);
                }

                //判断token是否过期
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime nowTime=LocalDateTime.now();
                LocalDateTime expireDateTime = LocalDateTime.parse(expireTime, df);

                if(expireDateTime.isBefore(nowTime)){
                    throw new LiFengException(ResultCodeEnum.TOKEN_INVALID);
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
