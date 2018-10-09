package com.missfresh.sys.service;

import com.missfresh.common.base.CoreService;
import com.missfresh.common.domain.Tree;
import com.missfresh.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptService extends CoreService<DeptDO> {
    
	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
