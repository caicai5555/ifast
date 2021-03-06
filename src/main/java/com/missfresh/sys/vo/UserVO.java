package com.missfresh.sys.vo;

import com.missfresh.sys.domain.UserDO;
import lombok.Data;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
@Data
public class UserVO {
    /**
     * 更新的用户对象
     */
    private UserDO userDO = new UserDO();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;
}
