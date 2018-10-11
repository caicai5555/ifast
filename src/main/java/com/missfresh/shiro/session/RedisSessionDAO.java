package com.missfresh.shiro.session;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/21 16:29 | caigl@missfresh.cn</small>
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private String activeSessionsCacheName;

    public RedisSessionDAO(String activeSessionsCacheName){
        this.activeSessionsCacheName = activeSessionsCacheName;
    }

    @Override public String getActiveSessionsCacheName() {
        return this.activeSessionsCacheName;
    }
}
