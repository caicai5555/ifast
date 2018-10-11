package com.missfresh.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.missfresh.common.base.CoreService;
import com.missfresh.common.domain.Tree;
import com.missfresh.sys.domain.MenuDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
@Service
public interface MenuService extends CoreService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);
}
