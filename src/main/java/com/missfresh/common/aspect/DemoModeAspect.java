package com.missfresh.common.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.missfresh.common.config.IFastConfig;
import com.missfresh.common.exception.IFastException;
import com.missfresh.common.type.EnumErrorCode;
import com.missfresh.common.utils.HttpContextUtils;

/**
 * <pre>
 * 演示模式切面处理
 * </pre>
 * 
 * <small> 2018年3月22日 | caigl@missfresh.cn</small>
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "ifast", name = "demoModel", havingValue = "true")
public class DemoModeAspect {

    private static final Logger log = LoggerFactory.getLogger(DemoModeAspect.class);

    @Autowired
    private IFastConfig config;

    @Pointcut("execution(* com.missfresh.**.controller.*.*(..))")
    public void testUserPointCut() {
    }

    @Around("testUserPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        if (config.isDemoMode()) {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            RequiresPermissions rp = method.getAnnotation(RequiresPermissions.class);
            if (rp != null) {
                String[] value = rp.value();
                List<String> asList = Arrays.asList(value);
                String string = asList.toString();
                if (!HttpContextUtils.getHttpServletRequest().getMethod().equalsIgnoreCase("GET")
                        && (string.contains("remove") || string.contains("Remove") || string.contains("edit")
                                || string.contains("update") || string.contains("delete"))) {
                    log.error("当前用户未演示账户无权操作！");
                    throw new IFastException(EnumErrorCode.notAuthorization.getCodeStr());
                }
            }
        }
        Object result = point.proceed();
        return result;
    }

}
