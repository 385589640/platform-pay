package com.dy.platform.quartz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.dy.platform.quartz.entity.QuartzBean;
import com.dy.platform.quartz.service.QuartzJobService;

/**
 * @program: QuartzJobServiceImpl
 * @description:
 * @author: lsd
 * @create: 2020-07-21 17:00
 **/
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 创建定时任务Simple quartzBean.getInterval()==null表示单次提醒，
	 * 否则循环提醒（quartzBean.getEndTime()!=null）
	 * 
	 * @param quartzBean
	 */
	@Override
	public void createScheduleJobSimple(QuartzBean quartzBean) throws Exception {
		// 获取到定时任务的执行类 必须是类的绝对路径名称
		// 定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
		// 构建定时任务信息
		JobDetail jobDetail = JobBuilder.newJob(jobClass)
				.withIdentity(quartzBean.getJobName(),
						!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
				.setJobData(quartzBean.getJobDataMap()).build();
		// 设置定时任务执行方式
		SimpleScheduleBuilder simpleScheduleBuilder = null;
		if (quartzBean.getInterval() == null) { // 单次
			simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
		} else { // 循环
			simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(quartzBean.getInterval());
		}
		// 构建触发器trigger
		Trigger trigger = null;
		if (quartzBean.getInterval() == null) { // 单次
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(quartzBean.getJobName(),
							!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
					.withSchedule(simpleScheduleBuilder).startAt(quartzBean.getStartTime()).build();
		} else { // 循环
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(quartzBean.getJobName(),
							!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
					.withSchedule(simpleScheduleBuilder).startAt(quartzBean.getStartTime())
					.endAt(quartzBean.getEndTime()).build();
		}
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 创建定时任务Cron 定时任务创建之后默认启动状态
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws Exception
	 */
	@Override
	public void createScheduleJobCron(QuartzBean quartzBean) throws Exception {
		// 获取到定时任务的执行类 必须是类的绝对路径名称
		// 定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
		// 构建定时任务信息
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName())
				.setJobData(quartzBean.getJobDataMap()).build();
		// 设置定时任务执行方式
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
		// 构建触发器trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName())
				.withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 根据任务名称暂停定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws Exception
	 */
	@Override
	public void pauseScheduleJob(String jobName, String jobGroup) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 根据任务名称恢复定时任务
	 * 
	 * @param jobName  定时任务名
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	@Override
	public void resumeScheduleJob(String jobName, String jobGroup) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 根据任务名称立即运行一次定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	@Override
	public void runOnce(String jobName, String jobGroup) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新定时任务Simple
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws SchedulerException
	 */
	@Override
	public void updateScheduleJobSimple(QuartzBean quartzBean) throws Exception {
		// 获取到对应任务的触发器
		TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName(),
				!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null);
		// 设置定时任务执行方式
		SimpleScheduleBuilder simpleScheduleBuilder = null;
		if (quartzBean.getInterval() == null) { // 单次
			simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
		} else { // 循环
			simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(quartzBean.getInterval());
		}
		// 构建触发器trigger
		Trigger trigger = null;
		if (quartzBean.getInterval() == null) { // 单次
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(quartzBean.getJobName(),
							!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
					.withSchedule(simpleScheduleBuilder).startAt(quartzBean.getStartTime()).build();
		} else { // 循环
			TriggerBuilder.newTrigger()
					.withIdentity(quartzBean.getJobName(),
							!ObjectUtils.isEmpty(quartzBean.getJobGroup()) ? quartzBean.getJobGroup() : null)
					.withSchedule(simpleScheduleBuilder).startAt(quartzBean.getStartTime())
					.endAt(quartzBean.getEndTime()).build();
		}
		// 重置对应的job
		scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * 更新定时任务Cron
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws SchedulerException
	 */
	@Override
	public void updateScheduleJobCron(QuartzBean quartzBean) throws Exception {
		// 获取到对应任务的触发器
		TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
		// 设置定时任务执行方式
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
		// 重新构建任务的触发器trigger
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		// 重置对应的job
		scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * 根据定时任务名称从调度器当中删除定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	@Override
	public void deleteScheduleJob(String jobName, String jobGroup) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 获取任务状态
	 * 
	 * @param jobName
	 * @param jobGroup 任务组（没有分组传值null）
	 * @return (" BLOCKED ", " 阻塞 "); ("COMPLETE", "完成"); ("ERROR", "出错"); ("NONE",
	 *         "不存在"); ("NORMAL", "正常"); ("PAUSED", "暂停");
	 */
	@Override
	public String getScheduleJobStatus(String jobName, String jobGroup) throws Exception {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
		return state.name();
	}

	/**
	 * 根据定时任务名称来判断任务是否存在
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	@Override
	public Boolean checkExistsScheduleJob(String jobName, String jobGroup) throws Exception {
		JobKey jobKey = JobKey.jobKey(jobName, !ObjectUtils.isEmpty(jobGroup) ? jobGroup : null);
		return scheduler.checkExists(jobKey);
	}

	/**
	 * 根据任务組刪除定時任务
	 * 
	 * @param jobGroup 任务组
	 * @throws SchedulerException
	 */
	@Override
	public Boolean deleteGroupJob(String jobGroup) throws Exception {
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
		Set<JobKey> jobkeySet = scheduler.getJobKeys(matcher);
		List<JobKey> jobkeyList = new ArrayList<JobKey>();
		jobkeyList.addAll(jobkeySet);
		return scheduler.deleteJobs(jobkeyList);
	}

	/**
	 * 根据任务組批量刪除定時任务
	 * 
	 * @param jobkeyList
	 * @throws SchedulerException
	 */
	@Override
	public Boolean batchDeleteGroupJob(List<JobKey> jobkeyList) throws Exception {
		return scheduler.deleteJobs(jobkeyList);
	}

	/**
	 * 根据任务組批量查询出jobkey
	 * 
	 * @param jobGroup 任务组
	 * @throws SchedulerException
	 */
	@Override
	public void batchQueryGroupJob(List<JobKey> jobkeyList, String jobGroup) throws Exception {
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
		Set<JobKey> jobkeySet = scheduler.getJobKeys(matcher);
		jobkeyList.addAll(jobkeySet);
	}
}