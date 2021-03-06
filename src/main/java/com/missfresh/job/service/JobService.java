package com.missfresh.job.service;

import org.quartz.SchedulerException;

import com.missfresh.common.base.CoreService;
import com.missfresh.job.domain.TaskDO;


/**
 * <pre>
 * 定时任务
 * </pre>
 * <small> 2018年3月22日 | caigl@missfresh.cn</small>
 */
public interface JobService extends CoreService<TaskDO> {
	
	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;

	void runNowOnce(Long jobId)  throws SchedulerException ;
}
