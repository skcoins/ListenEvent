/**
 * 
 */
package com.softisland.listener;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.softisland.bean.utils.JRedisUtils;
import com.softisland.config.RedisKeyConfig;
import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Skcoin_sol_Skcoin;
import com.softisland.handler.CommonHandler;
import com.softisland.handler.SkCoinEventHandler;
import com.softisland.model.TranscationEvent;
import com.softisland.service.TranscationEventService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service
public class SkCoinContractListener {
	
	@Autowired
	Web3j web3j;
	
	@Autowired
	JRedisUtils jRedisUtils;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	@Autowired
	Skcoin_sol_Skcoin skcoin_sol_Skcoin;
	
	BigInteger nowBlockNumber = new BigInteger("2967247");
	
	/**
	 * 确认交易是否成功
	 */
	public void confirmEvent(){
		
		String nowBlock = null;
		try {
			nowBlock = jRedisUtils.getValue(RedisKeyConfig.BLOCK_NUMBER_NOW);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StringUtils.isNoneBlank(nowBlock)){
			nowBlockNumber = new BigInteger(nowBlock);
		}
		
		
		//通过当前块  查询 交易块+12 < 当前块的
		List<TranscationEvent> transcationEventList = transcationEventService.queryDefaultTranscationEvent(nowBlockNumber.longValue());
		
		for(TranscationEvent v : transcationEventList){
			try {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(v.getTranscationHash()).send();
				
				TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
				
				
				int ret = 0 ;
				if(transactionReceipt != null && transactionReceipt.isStatusOK()){
					ret = transcationEventService.updateTranscationEvent(TranscationEvent.builder()
							.id(v.getId())
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().toString())
							.confirmBlockNumber(nowBlockNumber.longValue())
							.build(), null);
				} else {
					 ret = transcationEventService.updateTranscationEvent(TranscationEvent.builder()
							.id(v.getId())
							.status(TrascationStatusEum.FAIL_STATUS.getStatus().shortValue())
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().toString())
							.confirmBlockNumber(nowBlockNumber.longValue())
							.build(), 1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * skcoin 事件监听
	 */
	public void ethTokensListener(){
		BigInteger maxBlockNumber = transcationEventService.getMaxBlockNumber();
		
		try {
			if(maxBlockNumber == null){
				maxBlockNumber = new BigInteger(jRedisUtils.getValue(RedisKeyConfig.BLOCK_NUMBER_NOW));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		
//		 EthFilter filter = new EthFilter(startBlockNumber, DefaultBlockParameterName.LATEST, skcoin_sol_Skcoin.getContractAddress());
//		 filter.addOptionalTopics(EventEncoder.encode(Skcoin_sol_Skcoin.ONTOKENPURCHASE_EVENT),
//				 				  EventEncoder.encode(Skcoin_sol_Skcoin.ONTOKENSELL_EVENT),
//				 				 EventEncoder.encode(Skcoin_sol_Skcoin.ASSETSDETAIL_EVENT));
//		 
//		 web3j.ethLogObservable(filter).subscribe(v -> {
//			 
//			 if(v.getTopics().contains(EventEncoder.encode(Skcoin_sol_Skcoin.ONTOKENPURCHASE_EVENT))){
//				 EventValues eventValues = Contract.staticExtractEventParameters(Skcoin_sol_Skcoin.ONTOKENPURCHASE_EVENT,v);
//				 OnTokenPurchaseEventResponse typedResponse = new OnTokenPurchaseEventResponse();
//				 typedResponse.log = v;
//	             typedResponse.customerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
//                 typedResponse.incomingEthereum = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//                 typedResponse.tokensMinted = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//                 typedResponse.tokenPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
//                 typedResponse.divChoice = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
//                 typedResponse.referrer = (String) eventValues.getNonIndexedValues().get(4).getValue();
//                 
//                 CommonHandler.commonPool.execute(new SkCoinEventHandler<>(typedResponse, transcationEventService));
//			 }
//			 
//			
//		 });
		
		//eth 换 token
		onTokenPurchaseEvent(startBlockNumber);
		//token 换 eth
		onTokenSellEvent(startBlockNumber);
		//分红
		assetsDetailEvent(startBlockNumber);
		
	}
	
	/**
	 * eth 换 token
	 * @param startBlockNumber
	 */
	public void onTokenPurchaseEvent(DefaultBlockParameter startBlockNumber){
		skcoin_sol_Skcoin.onTokenPurchaseEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v -> {
			CommonHandler.commonPool.execute(new SkCoinEventHandler<>(v, transcationEventService));
		},error -> {
			log.error("巡检发生异常({})",error);
		});
	}
	
	/**
	 * token 换 eth
	 * @param startBlockNumber
	 */
	public void onTokenSellEvent(DefaultBlockParameter startBlockNumber){
		skcoin_sol_Skcoin.onTokenSellEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v -> {
			CommonHandler.commonPool.execute(new SkCoinEventHandler<>(v, transcationEventService));
		},error -> {
			log.error("巡检发生异常({})",error);
		});
	}
	
	/**
	 * 分红
	 * @param startBlockNumber
	 */
	public void assetsDetailEvent(DefaultBlockParameter startBlockNumber){
		skcoin_sol_Skcoin.assetsDetailEventObservable(startBlockNumber,  DefaultBlockParameterName.LATEST).subscribe(v -> {
			CommonHandler.commonPool.execute(new SkCoinEventHandler<>(v, transcationEventService));;
		},error -> {
			log.error("巡检发生异常({})",error);
		});
	}
}
