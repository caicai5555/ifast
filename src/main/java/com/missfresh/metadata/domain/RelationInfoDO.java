package com.missfresh.metadata.domain;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import com.missfresh.common.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2018-10-09 18:55:03 | missfresh</small>
 */
@Data
@SuppressWarnings("serial")
@TableName("relation_info")
@EqualsAndHashCode(callSuper=false)
public class RelationInfoDO {
	@TableId
	private Long id;

    /** 所属模块id */
    private Integer module_id;

    /** etl_task_id */
    private Integer etl_task_id;

    /** 所属模块名称 */
    private String module_name;

    /** 任务名称 */
    private String etl_task_name;

    /** 任务类型 hive,mysql,hive2mysql,local2hive */
    private String etl_type;

    /** 目标类型 hive,mysql,mail */
    private String target_type;

    /** 目标数据库名称，如果target_type为mail，则此处为blg_data_mail */
    private String target_db_name;

    /** 目标表名 */
    private String target_table_name;

    /** 源类型 hive,mysql,local */
    private String source_type;

    /** 源数据库名称，如果source_type为local，则此处为local */
    private String source_db_name;

    /** 源表名 */
    private String source_table_name;

    /** 源表使用次数 */
    private Integer use_count;

}
