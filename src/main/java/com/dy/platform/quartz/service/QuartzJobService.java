package com.dy.platform.quartz.service;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.dy.platform.quartz.entity.QuartzBean;

/**
 * @program: QuartzJobService
 * @description:
 * @author: lsd
 * @create: 2020-07-21 17:00
 **/
@Service
public interface QuartzJobService {

	/**
	 * 创建定时任务Simple quartzBean.getInterval()==null表示单次提醒，
	 * 否则循环提醒（quartzBean.getEndTime()!=null）
	 * 
	 * @param quartzBean
	 */
	public void createScheduleJobSimple(QuartzBean quartzBean) throws Exception;

	/**
	 * 创建定时任务Cron 定时任务创建之后默认启动状态
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws Exception
	 */
	public void createScheduleJobCron(QuartzBean quartzBean) throws Exception;

	/**
	 * 根据任务名称暂停定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws Exception
	 */
	public void pauseScheduleJob(String jobName, String jobGroup) throws Exception;

	/**
	 * 根据任务名称恢复定时任务
	 * 
	 * @param jobName  定时任务名
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	public void resumeScheduleJob(String jobName, String jobGroup) throws Exception;

	/**
	 * 根据任务名称立即运行一次定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	public void runOnce(String jobName, String jobGroup) throws Exception;

	/**
	 * 更新定时任务Simple
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws SchedulerException
	 */
	public void updateScheduleJobSimple(QuartzBean quartzBean) throws Exception;

	/**
	 * 更新定时任务Cron
	 * 
	 * @param quartzBean 定时任务信息类
	 * @throws SchedulerException
	 */
	public void updateScheduleJobCron(QuartzBean quartzBean) throws Exception;

	/**
	 * 根据定时任务名称从调度器当中删除定时任务
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	public void deleteScheduleJob(String jobName, String jobGroup) throws Exception;

	/**
	 * 获取任务状态
	 * 
	 * @param jobName
	 * @param jobGroup 任务组（没有分组传值null）
	 * @return (" BLOCKED ", " 阻塞 "); ("COMPLETE", "完成"); ("ERROR", "出错"); ("NONE",
	 *         "不存在"); ("NORMAL", "正常"); ("PAUSED", "暂停");
	 */
	public String getScheduleJobStatus(String jobName, String jobGroup) throws Exception;

	/**
	 * 根据定时任务名称来判断任务是否存在
	 * 
	 * @param jobName  定时任务名称
	 * @param jobGroup 任务组（没有分组传值null）
	 * @throws SchedulerException
	 */
	public Boolean checkExistsScheduleJob(String jobName, String jobGroup) throws Exception;

	/**
	 * 根据任务組刪除定時任务
	 * 
	 * @param jobGroup 任务组
	 * @throws SchedulerException
	 */
	public Boolean deleteGroupJob(String jobGroup) throws Exception;

	/**
	 * 根据任务組批量刪除定時任务
	 * 
	 * @param jobkeyList
	 * @throws SchedulerException
	 */
	public Boolean batchDeleteGroupJob(List<JobKey> jobkeyList) throws Exception;

	/**
	 * 根据任务組批量查询出jobkey
	 * 
	 * @param jobGroup 任务组
	 * @throws SchedulerException
	 */
	public void batchQueryGroupJob(List<JobKey> jobkeyList, String jobGroup) throws Exception;
}
