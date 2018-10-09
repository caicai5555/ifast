package com.missfresh.oss.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.missfresh.common.base.CoreServiceImpl;
import com.missfresh.common.config.IFastConfig;
import com.missfresh.common.utils.DateUtils;
import com.missfresh.common.utils.FileType;
import com.missfresh.oss.dao.FileDao;
import com.missfresh.oss.domain.FileDO;
import com.missfresh.oss.sdk.QiNiuOSSService;
import com.missfresh.oss.service.FileService;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class FileServiceImpl extends CoreServiceImpl<FileDao, FileDO> implements FileService {

    @Autowired
    private IFastConfig ifastConfig;
    @Autowired
    private QiNiuOSSService qiNiuOSS;

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        fileName = fileName.substring(0, fileName.indexOf(".")) + "-" + System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
        fileName = ifastConfig.getProjectName() + "/" + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_8)
                + "/" + fileName;
        String url = qiNiuOSS.upload(uploadBytes, fileName);
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, new Date());
        super.insert(sysFile);
        return url;
    }
}
