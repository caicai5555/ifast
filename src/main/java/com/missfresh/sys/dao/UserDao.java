package com.missfresh.sys.dao;

import com.missfresh.common.base.BaseDao;
import com.missfresh.sys.domain.UserDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface UserDao extends BaseDao<UserDO> {
	
	Long[] listAllDept();

}
