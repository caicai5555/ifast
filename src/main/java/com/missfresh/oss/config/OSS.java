package com.missfresh.oss.config;

import com.missfresh.common.domain.ConfigDO;
import com.missfresh.common.service.ConfigService;
import com.missfresh.common.utils.JSONUtils;
import com.missfresh.oss.sdk.OSSConfig;
import com.missfresh.oss.sdk.QiNiuOSSService;
import com.qiniu.common.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableCaching
public class OSS {
    
    @Autowired
    private ConfigService configService;
    
    @Bean
    public QiNiuOSSService qiNiuOSS() {
        ConfigDO configDO = configService.getByKey("oss_qiniu");
        String v = configDO.getV();
        Map<String, Object> json = JSONUtils.jsonToMap(v);
        String ak = (String) json.get("AccessKey");
        String sk = (String) json.get("SecretKey");
        String bucket = (String) json.get("bucket");
        String accessUrl = (String) json.get("AccessUrl");
        
        OSSConfig ossConfig = new OSSConfig();
        ossConfig.setQiNiuAccessKey(ak);
        ossConfig.setQiNiuSecretKey(sk);
        ossConfig.setQiNiuBucket(bucket);
        ossConfig.setQiNiuAccessURL(accessUrl);
        
        return new QiNiuOSSService(ossConfig, Zone.zone2());
    }

}
