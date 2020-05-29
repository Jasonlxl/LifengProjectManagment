package com.sd.lifeng.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sd.lifeng.config.JwtConfig;
import com.sd.lifeng.constant.TimeUnitCons;
import com.sd.lifeng.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Classname TokenServiceImpl
 * @Description
 * @Author bmr
 * @Date 2020/5/23 13:27:51
 */
@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String createToken(String userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowTime=LocalDateTime.now();
        LocalDateTime expireDateTime = null;
        int expireNumber=jwtConfig.getExpire();
        if(jwtConfig.getUnit() .equals(TimeUnitCons.DAY_UNIT) ){
            expireDateTime = nowTime.plusDays(expireNumber);
        }else if(jwtConfig.getUnit() .equals(TimeUnitCons.HOUR_UNIT) ){
            expireDateTime = nowTime.plusHours(expireNumber);
        }else if(jwtConfig.getUnit() .equals(TimeUnitCons.MINUTE_UNIT)){
            expireDateTime = nowTime.plusMinutes(expireNumber);
        }else if(jwtConfig.getUnit() .equals(TimeUnitCons.SECOND_UNIT)){
            expireDateTime = nowTime.plusSeconds(expireNumber);
        }else {
            expireDateTime = nowTime;
        }
        Date expireDate=Date.from(expireDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create().withAudience(userId).withExpiresAt(expireDate).sign(Algorithm.HMAC256(jwtConfig.getKey()));
    }

    @Override
    public int getUserId() {
        String token = request.getHeader("token");
        String userId= JWT.decode(token).getAudience().get(0);
        return Integer.parseInt(userId);
    }
}
