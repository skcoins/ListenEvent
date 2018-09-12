/**
 * 
 */
package com.softisland.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softisland.listener.ContractListener;

/**
 * @author Administrator
 *
 */
@Configurable
@EnableScheduling
@Component
public class TranscationEventSchedule {

	@Autowired
	ContractListener contractListener;
	
	
//	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void dealErrorTradeTask(){
		contractListener.confirmEvent();
	}
}
