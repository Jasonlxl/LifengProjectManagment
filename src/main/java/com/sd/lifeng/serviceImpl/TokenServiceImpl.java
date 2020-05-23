package com.sd.lifeng.serviceImpl;

import com.auth0.jwt.JWT;
import com.sd.lifeng.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname TokenServiceImpl
 * @Description TODO
 * @Author bmr
 * @Date 2020/5/23 13:27:51
 */
@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    HttpServletRequest request;

    @Override
    public int getUserId() {
        String token = request.getHeader("token");
        String userId= JWT.decode(token).getAudience().get(0);
        return Integer.parseInt(userId);
    }
}
