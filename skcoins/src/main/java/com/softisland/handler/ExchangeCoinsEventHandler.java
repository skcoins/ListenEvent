/**
 * 
 */
package com.softisland.handler;

import java.util.Date;

import com.softisland.config.EventNameEum;
import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Bankroll_sol_BankRoll.RedeemEventEventResponse;
import com.softisland.contract.Bankroll_sol_BankRoll.WithdrawEventEventResponse;
import com.softisland.model.ExchangeCoins;
import com.softisland.service.ExchangeCoinsService;

import lombok.Data;

/**
 * @author Administrator
 * @param <T>
 *
 */
@Data
public class ExchangeCoinsEventHandler<T> implements Runnable{
	
	private T response;
	
	private String eventName;
	
	private ExchangeCoinsService exchangeCoinsService;
	
	public ExchangeCoinsEventHandler(T response,ExchangeCoinsService exchangeCoinsService,String eventName){
		this.response = response;
		this.exchangeCoinsService = exchangeCoinsService;
		this.eventName = eventName;
	}
	

	@Override
	public void run() {
		if(response instanceof RedeemEventEventResponse){
			
			RedeemEventEventResponse v = (RedeemEventEventResponse)response;
			short currency = 1;
			 
			
			exchangeCoinsService.insertExchangeCoins(
					ExchangeCoins.builder()
					.blockHash(v.log.getBlockHash())
					.blockNumber(v.log.getBlockNumber().longValue())
					.createDate(new Date())
					.fromPerson(v.sender)
					.nums(v.amount.longValue())
					.isCall((short)0)
					.transcationHash(v.log.getTransactionHash())
					.status(Short.valueOf(TrascationStatusEum.DEFAULT_STATUS.getStatus().toString()))
					.eventName(eventName)
					.currency(currency)
					.build());
		}else if (response instanceof WithdrawEventEventResponse){
			WithdrawEventEventResponse v = (WithdrawEventEventResponse)response;
			short currency = 2;
			exchangeCoinsService.insertExchangeCoins(
					ExchangeCoins.builder()
					.blockHash(v.log.getBlockHash())
					.blockNumber(v.log.getBlockNumber().longValue())
					.createDate(new Date())
					.fromPerson(v.sender)
					.nums(v.amount.longValue())
					.isCall((short)0)
					.transcationHash(v.log.getTransactionHash())
					.status(Short.valueOf(TrascationStatusEum.DEFAULT_STATUS.getStatus().toString()))
					.eventName(eventName)
					.currency(currency)
					.build());
		}
	}

}
