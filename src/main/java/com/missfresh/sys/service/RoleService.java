package com.missfresh.sys.service;

import com.missfresh.common.base.CoreService;
import com.missfresh.sys.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | caigl@missfresh.cn</small>
 */
@Service
public interface RoleService extends CoreService<RoleDO> {
    List<RoleDO> findAll();
    List<RoleDO> findListStatusByUserId(Serializable id);
    List<RoleDO> findListByUserId(Serializable id);
}
