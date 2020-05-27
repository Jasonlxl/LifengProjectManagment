package com.sd.lifeng.vo.user;

import com.sd.lifeng.vo.auth.ResourceVO;
import lombok.Data;

import java.util.Set;

/**
 * @Classname LoginResponseVO
 * @Description
 * @Author bmr
 * @Date 2020/5/24 9:28:51
 */
@Data
public class LoginResponseVO {

    private String token;

    private int id;

    private String userName;

    private String realName;

    private int userType;

    private String userTypeRemark;

    private String createTime;

    private Set<ResourceVO> resourceVOList;
}
