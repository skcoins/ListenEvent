/**
 * 
 */
package com.softisland.handler;

import java.util.Date;
import java.util.List;

import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Bankroll_sol_BankRoll.LedgerRecordEventEventResponse;
import com.softisland.contract.Bankroll_sol_BankRoll.PointToTokenEventEventResponse;
import com.softisland.contract.Bankroll_sol_BankRoll.TokenToPointEventEventResponse;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.LedgerEvent;
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
		if(response instanceof TokenToPointEventEventResponse){
			
			TokenToPointEventEventResponse v = (TokenToPointEventEventResponse)response;
			short currency = 1;
			
			exchangeCoinsService.insertExchangeCoins(
					ExchangeCoins.builder()
					.businessId(v._id.longValue())
					.blockHash(v.log.getBlockHash())
					.blockNumber(v.log.getBlockNumber().longValue())
					.createDate(new Date())
					.tradePerson(v._recharger)
					.nums(v._amount.toString())
					.isCall((short)0)
					.transcationHash(v.log.getTransactionHash())
					.status(Short.valueOf(TrascationStatusEum.DEFAULT_STATUS.getStatus().toString()))
					.eventName(eventName)
					.currency(currency)
					.build());
		}else if (response instanceof PointToTokenEventEventResponse){
			PointToTokenEventEventResponse v = (PointToTokenEventEventResponse)response;
			short currency = 2;
			exchangeCoinsService.insertExchangeCoins(
					ExchangeCoins.builder()
					.businessId(v._id.longValue())
					.blockHash(v.log.getBlockHash())
					.blockNumber(v.log.getBlockNumber().longValue())
					.createDate(new Date())
					.tradePerson(v.sender)
					.nums(v.amount.toString())
					.isCall((short)0)
					.transcationHash(v.log.getTransactionHash())
					.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
					.eventName(eventName)
					.currency(currency)
					.build());
		}else if(response instanceof LedgerRecordEventEventResponse){
			LedgerRecordEventEventResponse v = (LedgerRecordEventEventResponse)response;
			
			List<LedgerEvent> list = exchangeCoinsService.queryLedgerEvent(v._id.longValue());
			
			if(list != null && list.size() > 0){
				LedgerEvent ledgerEvent = list.get(0);
				exchangeCoinsService.updateledgerEvent(LedgerEvent.builder()
						.id(ledgerEvent.getId())
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.isCall((short)0)
						.transcationHash(v.log.getTransactionHash())
						.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
						.eventName(eventName)
						.build(), 1);
			} else {
				exchangeCoinsService.insertledgerEvent(LedgerEvent.builder()
						.businessId(v._id.longValue())
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.isCall((short)0)
						.transcationHash(v.log.getTransactionHash())
						.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
						.eventName(eventName)
						.build());
			}
		}
	}

}
