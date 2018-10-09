package com.missfresh.common.utils;

import com.missfresh.api.service.AppUserService;
import com.missfresh.api.util.JWTUtil;
import com.missfresh.sys.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static UserDO getSysUser() {
        Object principal = getSubjct().getPrincipal();
        if(principal instanceof String){
            String token = (String)principal;
            String userId = JWTUtil.getUserId(token);
            UserDO userDO = SpringContextHolder.getBean(AppUserService.class).selectById(userId);
            return userDO;
        }
        return (UserDO)getSubjct().getPrincipal();
	}
	public static Long getUserId() {
		return getSysUser().getId();
	}
	
	public static void logout() {
		getSubjct().logout();
	}
}
