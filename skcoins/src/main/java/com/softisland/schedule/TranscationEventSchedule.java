/**
 * 
 */
package com.softisland.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softisland.listener.BankRollContractListener;
import com.softisland.listener.SkCoinContractListener;
import com.softisland.service.CallBackService;

/**
 * @author Administrator
 *
 */
@Configurable
@EnableScheduling
@Component
public class TranscationEventSchedule {

	@Autowired
	BankRollContractListener bankRollContractListener;
	
	@Autowired
	SkCoinContractListener skCoinContractListener;
	
	@Autowired
	CallBackService callBackService;
	
	
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void confirmEvent(){
		skCoinContractListener.confirmEvent();
	}
	
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void confirmExchangeEvent(){
		bankRollContractListener.confirmExchangeEvent();
	}
	
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void bankRollCallBack(){
		callBackService.bankRollCallBack();
	}
	
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void skcoinCallBack(){
		callBackService.skcoinCallBack();
	}
}
