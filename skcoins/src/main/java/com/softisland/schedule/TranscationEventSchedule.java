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
	
	/**
	 * skcoin 块确认
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void confirmEvent(){
		skCoinContractListener.confirmEvent();
	}
	
	/**
	 * 分红事件 块确认
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void confirmBonusEvent(){
		skCoinContractListener.confirmBonusEvent();
	}
	
	/**
	 * 块确认 对bankroll的事件 进行确认
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void confirmExchangeEvent(){
		bankRollContractListener.confirmExchangeEvent();
	}
	
	/**
	 * bankroll 回调 包含 token换积分 积分换token
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void bankRollCallBack(){
		callBackService.bankRollCallBack();
	}
	
	/**
	 * skcoin 回调 包含 eth换token  token换eth
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void skcoinCallBack(){
		callBackService.skcoinCallBack();
	}
	
	/**
	 * 分红回调
	 */
	@Scheduled(fixedDelay=1000*10,initialDelay=1000*5)
    public void bonusCallBack(){
		callBackService.bonusCallBack();
	}
	
	
}
