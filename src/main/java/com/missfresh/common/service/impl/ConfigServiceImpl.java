package com.missfresh.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.missfresh.common.base.CoreServiceImpl;
import com.missfresh.common.dao.ConfigDao;
import com.missfresh.common.domain.ConfigDO;
import com.missfresh.common.service.ConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月6日 | caigl@missfresh.cn</small>
 */
@Service
public class ConfigServiceImpl extends CoreServiceImpl<ConfigDao, ConfigDO> implements ConfigService {
    
    @Override
    public ConfigDO getByKey(String k) {
        ConfigDO entity = new ConfigDO();
        entity.setK(k);
        return baseMapper.selectOne(entity);
    }
    
    @Override
    public String getValuByKey(String k) {
        ConfigDO bean = this.getByKey(k);
        return bean == null ? "" : bean.getV();
    }
    
    @Override
    public void updateKV(Map<String, String> kv) {
        for(Map.Entry<String, String> entry : kv.entrySet()){
            ConfigDO config = this.getByKey(entry.getKey());
            config.setV(entry.getValue());
            super.updateById(config);
        }
    }
    
    @Override
    public List<ConfigDO> findListByKvType(int kvType){
        ConfigDO configDO = new ConfigDO();
        configDO.setKvType(kvType);
        EntityWrapper<ConfigDO> ew = new EntityWrapper<>(configDO);
        List<ConfigDO> list = super.selectList(ew);
        return list;
    }
    
    
}
