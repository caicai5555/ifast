package com.missfresh.common.service;

import com.missfresh.common.base.CoreService;
import com.missfresh.common.domain.ConfigDO;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月6日 | caigl@missfresh.cn</small>
 */
public interface ConfigService extends CoreService<ConfigDO> {
    ConfigDO getByKey(String k);

    String getValuByKey(String k);
    
    void updateKV(Map<String, String> kv);
    
    List<ConfigDO> findListByKvType(int kvType);
}
