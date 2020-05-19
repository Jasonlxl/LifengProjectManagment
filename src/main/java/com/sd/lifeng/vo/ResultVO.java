package com.sd.lifeng.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author bmr
 * @time 2020-04-09 10:29
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 6437621327212302146L;

    /** 返回码. */
    private String code;

    /** 返回信息描述. */
    private String msg;

    /** 返回内容. */
    private T data;


}
