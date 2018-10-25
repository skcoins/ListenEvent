/**
 * 
 */
package com.softisland.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author wb
 *
 */

@Configuration
public class ScheduleConfig implements SchedulingConfigurer{

	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        taskRegistrar.setScheduler(taskScheduler());
    }

	@Bean(destroyMethod = "shutdown")
	public ScheduledExecutorService taskScheduler(){
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		return scheduler;
	}
}
