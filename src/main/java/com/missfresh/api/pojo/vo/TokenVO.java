package com.missfresh.api.pojo.vo;

import lombok.Data;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | caigl@missfresh.cn</small>
 */
@Data
public class TokenVO {
    private String token;
    private Long tokenExpire;
    private String refleshToken;
    private Long refreshTokenExpire;

}
