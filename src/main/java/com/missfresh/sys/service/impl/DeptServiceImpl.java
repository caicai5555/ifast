package com.missfresh.sys.service.impl;

import com.missfresh.common.base.CoreServiceImpl;
import com.missfresh.common.domain.Tree;
import com.missfresh.common.utils.BuildTree;
import com.missfresh.sys.dao.DeptDao;
import com.missfresh.sys.domain.DeptDO;
import com.missfresh.sys.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
@Service
public class DeptServiceImpl extends CoreServiceImpl<DeptDao, DeptDO> implements DeptService {

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = baseMapper.selectList(null);
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // 查询部门以及此部门的下级部门
        int result = baseMapper.getDeptUserNumber(deptId);
        return result == 0;
    }

}
