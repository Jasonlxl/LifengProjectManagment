package com.sd.lifeng.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Verification;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.config.JwtConfig;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IUserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserCategoryService userCategoryService;

    @Autowired
    private JwtConfig jwtConfig;

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

        //todo jwt这里好像没有进行校验  抽时间研究一下jwt

        //检查需不需要验证token
        if(method.isAnnotationPresent(VerifyToken.class)){
            VerifyToken userLoginToken = method.getAnnotation(VerifyToken.class);
            if(userLoginToken.required()){
                if(StringUtils.isEmpty(token)){
                    throw new LiFengException(ResultCodeEnum.TOKEN_MISS);
                }


                //验证token
                JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256((jwtConfig.getKey()))).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e){
                    throw new LiFengException(ResultCodeEnum.SERVER_EXCEPTION.getCode(),"jwt解析异常");
                }


                //获取token中的userId
                String userId;
                LocalDateTime expireTime=JWT.decode(token).getExpiresAt().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();;
                //判断token是否过期
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime nowTime=LocalDateTime.now();

                if(expireTime.isBefore(nowTime)){
                    throw new LiFengException(ResultCodeEnum.TOKEN_INVALID);
                }

                try {
                    userId= JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException e){
                    throw new LiFengException(ResultCodeEnum.TOKEN_ILLEGAL);
                }

                UserDO userDO = userCategoryService.findUserById(Integer.parseInt(userId));
                if(userDO == null){
                    throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
                }


                return true;
            }
        }

        return true;
    }

}
