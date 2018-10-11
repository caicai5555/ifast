package com.missfresh.metadata.service;

import com.missfresh.metadata.domain.RelationInfoDO;
import com.missfresh.common.base.CoreService;

import java.util.HashMap;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2018-10-09 18:55:03 | missfresh</small>
 */
public interface RelationInfoService extends CoreService<RelationInfoDO> {

    HashMap<String,Object> nodeJson(String sourceDbName, String sourceTableName);
}
