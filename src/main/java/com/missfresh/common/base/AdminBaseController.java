package com.missfresh.common.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.plugins.Page;
import com.missfresh.common.utils.HttpContextUtils;
import com.missfresh.common.utils.ShiroUtils;
import com.missfresh.sys.domain.UserDO;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <small> 2018年2月25日 | caigl@missfresh.cn</small>
 */
public abstract class AdminBaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public UserDO getUser() {
        return ShiroUtils.getSysUser();
    }

    public Long getUserId() {
        return getUser().getId();
    }

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    /**
     * <pre>
     * 自动获取分页参数，返回分页对象page
     * </pre>
     * 
     * <small> 2018年4月15日 | caigl@missfresh.cn</small>
     * 
     * @param e
     * @return
     */
    public <E> Page<E> getPage(Class<E> e) {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);
        return new Page<>(pageNumber, pageSize);
    }

    private int getParaToInt(String key, int defalut) {
        String pageNumber = HttpContextUtils.getHttpServletRequest().getParameter(key);
        if (StringUtils.isBlank(pageNumber)) {
            return defalut;
        }
        return Integer.parseInt(pageNumber);
    }
}