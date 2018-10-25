/**
 * 
 */
package com.softisland.handler;

import java.math.BigDecimal;
import java.util.Date;

import com.softisland.config.EventNameEum;
import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Skcoin_sol_Skcoin.BoughtAssetsDetailEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin.DivideEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin.OnTokenPurchaseEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin.OnTokenSellEventResponse;
import com.softisland.model.BonusEvent;
import com.softisland.model.TranscationEvent;
import com.softisland.service.TranscationEventService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @param <T>
 *
 */
@Slf4j
public class SkCoinEventHandler<T> implements Runnable{
	
	private T t;
	
	private TranscationEventService transcationEventService;
	
	public SkCoinEventHandler(T t,TranscationEventService transcationEventService){
		this.t = t;
		this.transcationEventService = transcationEventService;
	}

	@Override
	public void run() {
		try {
			if(t instanceof OnTokenPurchaseEventResponse){
				OnTokenPurchaseEventResponse v = (OnTokenPurchaseEventResponse)t;
				transcationEventService.insertTranscationEvent(TranscationEvent.builder()
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.currency((short)0)
						.eventName(EventNameEum.ON_TOKEN_PURCHASE_EVENT.getName())
						.divChoice(v.divChoice.toString())
						.nums(v.incomingEthereum.toString())
						.referredBy(v.referrer)
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
						.tokenPrice(v.tokenPrice.toString())
						.tokensMinted(v.tokensMinted.toString())
						.tradePerson(v.customerAddress)
						.transcationHash(v.log.getTransactionHash())
						.isCall((short)0)
						.build());
			}else if (t instanceof OnTokenSellEventResponse){
				OnTokenSellEventResponse v = (OnTokenSellEventResponse)t;
				transcationEventService.insertTranscationEvent(TranscationEvent.builder()
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.currency((short)1)
						.eventName(EventNameEum.ON_TOKEN_SELL_EVENT.getName())
						.nums(v.tokensBurned.toString())
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
						.tradePerson(v.customerAddress)
						.transcationHash(v.log.getTransactionHash())
						.ethMinted(v.ethereumEarned.toString())
						.isCall((short)0)
						.divChoice(v.divRate.toString())
						.build());
				
			}else if (t instanceof BoughtAssetsDetailEventResponse){
				BoughtAssetsDetailEventResponse v = (BoughtAssetsDetailEventResponse)t;
				transcationEventService.insertBonusEvent(BonusEvent.builder()
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.eventName(EventNameEum.ASSETS_DETAIL_EVENT.getName())
						.referrer(v.referrer) //推荐人
						.referrerToken(v.referrerToken.toString())//推荐人分红
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
						.tokenHolder(v.tokenHolder.toString())//持币者分红
						.tradePerson(v.buyer) //购买者
						.transcationHash(v.log.getTransactionHash())
						.isCall((short)0)
						.platformToken(v.toPlatformToken.toString())
						.build());
			}else if (t instanceof DivideEventResponse){
				DivideEventResponse v = (DivideEventResponse)t;
				transcationEventService.insertBonusEvent(BonusEvent.builder()
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.createDate(new Date())
						.eventName(EventNameEum.DIVID_EVENT.getName())
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
						.tradePerson(v.customerAddress)
						.tokenHolder(v.totalToken.toString())
						.transcationHash(v.log.getTransactionHash())
						.isCall((short)0)
						.build());
				
			}
		} catch (Exception e) {
			log.error("SKCOINEVENT写入数据库异常（{}）",e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(new BigDecimal("368934881474191032").divide(new BigDecimal("2").pow(64)).setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
		System.out.println(Math.pow(2, 4));
	}
}
