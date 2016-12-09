package com.tgi.safeher.service.manager.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@ComponentScan("com.tgi.safeher.service")
public class QuartzJobConfigManager {

	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("checkForAppInstallUnInstallStatus");
		return obj;
	}

	@Bean
	public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory
				.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
		stFactory.setStartDelay(10000);
		stFactory.setRepeatInterval(43200000);//1800000 mean after every 30 minutes, 43200000 mean after every 12 hours
//		stFactory.setRepeatCount(5);
		return stFactory;
	}
	
	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingPromotionExpiryFactoryBean() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("promotionExpiryInActive");
		return obj;
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingGiftExpiryFactoryBean() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("giftRidesInActiveMethod");
		return obj;
	}
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingPromotionExpiryFactoryBean().getObject());
		stFactory.setStartDelay(4000);
		stFactory.setName("PromotionInfo&UserPromotionExpiry");
		stFactory.setGroup("Promotion");
		stFactory.setCronExpression("0 0 0/1 1/1 * ? *");
		return stFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBeanForGiftRides(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingGiftExpiryFactoryBean().getObject());
		stFactory.setStartDelay(3000);
		stFactory.setName("GiftRidesExpiry");
		stFactory.setGroup("GiftRide");
		stFactory.setCronExpression("0 0 0/1 1/1 * ? *");
		return stFactory;
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingForDriverOnlineTrackTime() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("dumpDataIntoMongoForDriverOnlineTrackTime");
		return obj;
	}

	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBeanForDriverOnlineTrackTime(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingPromotionExpiryFactoryBean().getObject());
		stFactory.setStartDelay(4000);
		stFactory.setName("cronTriggerFactoryBeanForDriverOnlineTrackTime");
		stFactory.setGroup("OnlineTime");
		stFactory.setCronExpression("0 59 23 1/1 * ? *");//on every day at 11:59 PM
		return stFactory;
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingForSendIncompleteDataToUsers() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("sendIncompleteDataToUsers");
		return obj;
	}
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBeanForSendIncompleteDataToUsers(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingPromotionExpiryFactoryBean().getObject());
		stFactory.setStartDelay(4000);
		stFactory.setName("cronTriggerFactoryBeanForSendIncompleteDataToUsers");
		stFactory.setGroup("sendIncompleteDataToUsers");
		stFactory.setCronExpression("0 10 23 1/1 * ? *");//on every day at 11:10 PM
		return stFactory;
	}
	
	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBeanForEmailUserForIcCompleteData(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingPromotionExpiryFactoryBean().getObject());
		stFactory.setStartDelay(4000);
		stFactory.setName("quartzJobForAppInstallUnInstallStatus");
		stFactory.setGroup("getIncompleteSignInEmailJobs");
		stFactory.setCronExpression("0 0 12 1/1 * ? *");//on every day at 12:00 PM
		return stFactory;
	}
	
	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingForDriverDrivingDetailSqlDump() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("quartzJobForAppInstallUnInstallStatus");
		obj.setTargetMethod("saveDriverDrivingDetailIntoSql");
		return obj;
	}
		
	
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBeanDriverDrivingDetail(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingForDriverDrivingDetailSqlDump().getObject());
		stFactory.setStartDelay(4000);
		stFactory.setName("quartzJobForAppInstallUnInstallStatus");
		stFactory.setGroup("MongoToSqlDump");
		//stFactory.setCronExpression("0 0 8 1/1 * ? *");//on every day at 8:00 AM
		stFactory.setCronExpression("0 0/1 * 1/1 * ? *");//on every mint
	 	
		return stFactory;
	}
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(simpleTriggerFactoryBean().getObject(), 
				cronTriggerFactoryBean().getObject(), cronTriggerFactoryBeanForGiftRides().getObject(), 
				cronTriggerFactoryBeanForDriverOnlineTrackTime().getObject(), 
				cronTriggerFactoryBeanForSendIncompleteDataToUsers().getObject(),cronTriggerFactoryBeanDriverDrivingDetail().getObject());
		return scheduler;
	}
	
	
}
