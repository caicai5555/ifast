package com.missfresh.common.aspect;

import com.missfresh.api.util.JWTUtil;
import com.missfresh.common.annotation.Log;
import com.missfresh.common.base.BaseDO;
import com.missfresh.common.dao.LogDao;
import com.missfresh.common.domain.LogDO;
import com.missfresh.common.utils.*;
import com.missfresh.sys.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * 日志切面
 * </pre>
 * <small> 2018年3月22日 | caigl@missfresh.cn</small>
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogDao logMapper;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.missfresh.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }
    
    @Pointcut("execution(public * com.missfresh.*.controller.*.*(..))")
    public void logController(){}
    
    /** 记录controller日志，包括请求、ip、参数、响应结果 */
    @Around("logController()")
    public Object controller(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("{} {} {} {}.{}{}", request.getMethod(), request.getRequestURI(), IPUtils.getIpAddr(request), point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));
        
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        
        log.info("result({}) {}", time, JSONUtils.beanToJson(result));
        return result;
    }
    
    @Pointcut("execution(public * com.missfresh.*.service.*.*(..))")
    public void logService(){}
    
    /** 记录自定义service接口日志，如果要记录CoreService所有接口日志请仿照logMapper切面 */
    @Around("logService()")
    public Object service(ProceedingJoinPoint point) throws Throwable {
    	log.info("call {}.{}{}", point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));
    	
    	long beginTime = System.currentTimeMillis();
    	Object result = point.proceed();
    	long time = System.currentTimeMillis() - beginTime;
    	
    	log.info("result({}) {}", time, JSONUtils.beanToJson(result));
    	return result;
    }
    
    @Pointcut("within(com.baomidou.mybatisplus.mapper.BaseMapper+)")
    public void logMapper(){}
    
    /** 记录mapper所有接口日志，设置createBy和updateBy基础字段，logback会记录sql，这里记录查库返回对象 */
    @Around("logMapper()")
    public Object mapper(ProceedingJoinPoint point) throws Throwable {
    	String methodName = point.getSignature().getName();
    	boolean insertBy = false, updateBy = false;
    	switch(methodName) {
    	case "insert":
    	case "insertAllColumn":
    		insertBy = true;
    		break;
    	case "update":
    	case "updateById":
    	case "updateAllColumnById":
    		updateBy = true;
    		break;
    	}
    	if(insertBy || updateBy) {
    		Object arg0 = point.getArgs()[0];
    		if(arg0 instanceof BaseDO) {
    			try {
    				Subject subject = SecurityUtils.getSubject();
    				if(subject.isAuthenticated()) {
    					Object principal = subject.getPrincipal();
    					Long userId = null;
    					if(principal instanceof String) {
    						userId = Long.valueOf(JWTUtil.getUserId((String)principal));
    					}else if(principal instanceof UserDO) {
    						userId = ((UserDO)principal).getId();
    					}
    					BaseDO baseDO = (BaseDO)arg0;
    					if(insertBy) {
    						baseDO.setCreateBy(userId);
    					}
    					else if(updateBy) {
    						baseDO.setUpdateBy(userId);
    					}
    				}
    			}catch(Exception ignore) {}
    			log.info("call {}.{}{}", point.getTarget().getClass().getSimpleName(), methodName, Arrays.toString(point.getArgs()));
    		}
    	}
    	
    	long beginTime = System.currentTimeMillis();
    	Object result = point.proceed();
    	long time = System.currentTimeMillis() - beginTime;
    	
    	log.info("result({}) {}", time, JSONUtils.beanToJson(result));
    	return result;
    }

    /**
     * <pre>
     * 保存日志
     * </pre>
     * <small> 2018年3月22日 | caigl@missfresh.cn</small>
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        // Object[] args = joinPoint.getArgs();
        Map<String, String[]> parameterMap = HttpContextUtils.getHttpServletRequest().getParameterMap();
        try {
            String params = JSONUtils.beanToJson(parameterMap);
            int maxLength = 4999;
            if(params.length() > maxLength){
                params = params.substring(0, maxLength);
            }
            sysLog.setParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getSysUser();
        if (null == currUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId(-1L);
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUserId(-1L);
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUserId(ShiroUtils.getUserId());
            sysLog.setUsername(ShiroUtils.getSysUser().getUsername());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logMapper.insert(sysLog);
    }
}
