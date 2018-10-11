package com.missfresh.sys.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.missfresh.sys.domain.UserOnline;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
@Service
public interface SessionService {
	List<UserOnline> list(String name);

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
