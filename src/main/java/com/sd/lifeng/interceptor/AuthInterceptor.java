package com.sd.lifeng.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.service.IUserCategoryService;
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

        //检查需不需要验证token
        if(method.isAnnotationPresent(VerifyToken.class)){
            VerifyToken userLoginToken = method.getAnnotation(VerifyToken.class);
            if(userLoginToken.required()){
                if(StringUtils.isEmpty(token)){
                    throw new RuntimeException("该请求没有token,请先获取token!");
                }

                //获取token中的userId
                String userId;
                try {
                    userId= JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException e){
                    throw new RuntimeException("token非法,没有userId!");
                }
                UserDO userDO = userCategoryService.findUserById(Integer.parseInt(userId));
                if(userDO == null){
                    throw new RuntimeException("用户不存在，请重新登录!");
                }

                //验证token
                JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256((userDO.getPasswd()))).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e){
                    throw new RuntimeException("校验token发生异常");
                }
                return true;
            }
        }

        return true;
    }


}
