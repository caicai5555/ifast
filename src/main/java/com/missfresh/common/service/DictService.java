package com.missfresh.common.service;

import java.util.List;

import com.missfresh.common.base.CoreService;
import com.missfresh.common.domain.DictDO;
import com.missfresh.sys.domain.UserDO;

/**
 * <pre>
 * 数据字典
 * </pre>
 * 
 * <small> 2018年1月3日 | caigl@missfresh.cn</small>
 */
public interface DictService extends CoreService<DictDO> {
    
    List<DictDO> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     * 
     * @return
     * @param userDO
     */
    List<DictDO> getHobbyList(UserDO userDO);

    /**
     * 获取性别列表
     * 
     * @return
     */
    List<DictDO> getSexList();
}
