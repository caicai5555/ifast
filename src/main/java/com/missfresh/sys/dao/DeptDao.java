package com.missfresh.sys.dao;

import com.missfresh.common.base.BaseDao;
import com.missfresh.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
public interface DeptDao extends BaseDao<DeptDO> {
	
	Long[] listParentDept();
	
	int getDeptUserNumber(Long deptId);
}
