package com.missfresh.sys.dao;

import java.util.List;

import com.missfresh.common.base.BaseDao;
import com.missfresh.sys.domain.MenuDO;

/**
 * <pre>
 * 菜单管理
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
public interface MenuDao extends BaseDao<MenuDO> {
	
	List<MenuDO> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);
}
