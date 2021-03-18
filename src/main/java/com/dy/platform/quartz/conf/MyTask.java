package com.dy.platform.quartz.conf;

import java.time.LocalDateTime;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @program: job
 * @description:
 * @author: lsd
 * @create: 2020-06-02 18:07
 **/
@Component
public class MyTask extends QuartzJobBean {
	private static final Logger log = LoggerFactory.getLogger(MyTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap map = context.getJobDetail().getJobDataMap();
		String userId = map.getString("userId");
		log.info("SimpleJob says: " + jobKey + ", userId: " + userId + " executing at " + LocalDateTime.now());
	}

}
