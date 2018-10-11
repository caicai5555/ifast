package com.missfresh.metadata.dao;

import com.missfresh.metadata.domain.RelationInfoDO;
import com.missfresh.common.base.BaseDao;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2018-10-09 18:55:03 | missfresh</small>
 */
public interface RelationInfoDao extends BaseDao<RelationInfoDO> {

    HashMap<String,String> getDependents(String tableNames);

    List<HashMap<String,String>> getParentTable(String tableNames);

    List<HashMap<String,String>> getChlidTable(String s);
}
