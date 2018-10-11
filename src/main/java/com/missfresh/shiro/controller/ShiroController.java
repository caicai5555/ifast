package com.missfresh.shiro.controller;

import com.missfresh.common.type.EnumErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.missfresh.common.base.AdminBaseController;
import com.missfresh.common.utils.Result;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年5月1日 | caigl@missfresh.cn</small>
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController extends AdminBaseController {

    @RequestMapping("/405")
    public Result<String> http405() {
        return Result.build(EnumErrorCode.apiAuthorizationInvalid.getCode(), EnumErrorCode.apiAuthorizationInvalid.getMsg());
    }
    
    @RequestMapping("/500")
    public Result<String> http500() {
        return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
    }
}
