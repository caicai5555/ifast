package com.missfresh.common.dao;

import java.util.List;

import com.missfresh.common.base.BaseDao;
import com.missfresh.common.domain.DictDO;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
public interface DictDao extends BaseDao<DictDO>{

    List<DictDO> listType();
    
}
