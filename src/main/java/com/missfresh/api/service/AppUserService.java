package com.missfresh.api.service;

import com.missfresh.api.pojo.vo.TokenVO;
import com.missfresh.common.base.CoreService;
import com.missfresh.sys.domain.UserDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月27日 | caigl@missfresh.cn</small>
 */
public interface AppUserService extends CoreService<UserDO> {
    /** 申请token */
    TokenVO getToken(String uname, String passwd) ;
    /** 刷新token */
    TokenVO refreshToken(String uname, String refreshToken);
    /** 检查token是否有效：未超时、未注销*/
    void verifyToken(String token, boolean isRefresh);
    /** 注销token */
    void logoutToken(String token, String refreshToken);
}
