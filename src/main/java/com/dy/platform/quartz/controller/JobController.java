package com.dy.platform.quartz.controller;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dy.platform.quartz.entity.QuartzBean;
import com.dy.platform.quartz.service.QuartzJobService;

/**
 * @program: JobController
 * @description:
 * @author: lsd
 * @create: 2020-07-21 17:08
 **/
@RestController
@RequestMapping("/api/quartz/")
public class JobController {

	@Autowired
	private QuartzJobService quartzJobService;

	// 创建&启动
	@GetMapping("startSimpleJob")
	public String startSimpleJob() throws SchedulerException, ClassNotFoundException, ParseException {
		QuartzBean quartzBean = new QuartzBean();
		quartzBean.setJobClass("com.quartz.demo.job.MyTask");
		quartzBean.setJobName("job1");
		JobDataMap map = new JobDataMap();
		map.put("userId", "123456");
		quartzBean.setJobDataMap(map);
		Date now = new Date();
		quartzBean.setStartTime(DateUtils.addSeconds(now, 10));
		quartzBean.setInterval(10);
		quartzBean.setEndTime(DateUtils.addMinutes(now, 1));
		try {
			quartzJobService.createScheduleJobSimple(quartzBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "startJob Success!";
	}

	/**
	 * 创建cron Job
	 * 
	 * @param quartzBean
	 * @return
	 */
	@RequestMapping("/createCronJob")
	@ResponseBody
	public String createJob(QuartzBean quartzBean) {
		try {
			// 进行测试所以写死
			quartzBean.setJobClass("com.quartz.demo.job.MyTask");
			quartzBean.setJobName("job1");
			quartzBean.setCronExpression("*/5 * * * * ?");
			quartzJobService.createScheduleJobCron(quartzBean);
		} catch (Exception e) {
			return "创建失败";
		}
		return "创建成功";
	}

	/**
	 * 暂停job
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/pauseJob/{jobName}", "/pauseJob/{jobName}/{jobGroup}" })
	@ResponseBody
	public String pauseJob(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
		try {
			quartzJobService.pauseScheduleJob(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		} catch (Exception e) {
			return "暂停失败";
		}
		return "暂停成功";
	}

	@RequestMapping(value = { "/resume/{jobName}", "/resume/{jobName}/{jobGroup}" })
	@ResponseBody
	public String resume(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
		try {
			quartzJobService.resumeScheduleJob(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		} catch (Exception e) {
			return "启动失败";
		}
		return "启动成功";
	}

	@RequestMapping(value = { "/delete/{jobName}", "/delete/{jobName}/{jobGroup}" })
	public String delete(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
		try {
			quartzJobService.deleteScheduleJob(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		} catch (Exception e) {
			return "删除失败";
		}
		return "删除成功";
	}

	@RequestMapping(value = { "/check/{jobName}", "/check/{jobName}/{jobGroup}" })
	public String check(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
		try {
			if (quartzJobService.checkExistsScheduleJob(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null)) {
				return "存在定时任务：" + jobName;
			} else {
				return "不存在定时任务：" + jobName;
			}
		} catch (Exception e) {
			return "查询任务失败";
		}
	}

	@RequestMapping(value = { "/status/{jobName}", "/status/{jobName}/{jobGroup}" })
	@ResponseBody
	public String status(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
		try {
			return quartzJobService.getScheduleJobStatus(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		} catch (Exception e) {
			return "获取状态失败";
		}
		// return "获取状态成功";
	}

}
