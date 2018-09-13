/**
 * 
 */
package com.softisland.listener;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import com.softisland.config.EventNameEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.contract.Small_sol_small;
import com.softisland.handler.RecordEventHandler;
import com.softisland.model.TranscationEvent;
import com.softisland.service.TranscationEventService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service
public class ContractListener {

	@Autowired
	Web3j web3j;
	
	@Autowired
	Web3j web3jInfura;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	BigInteger nowBlockNumber = new BigInteger("2967247");
	
	/**
	 * 监听并得到最新块
	 */
	@PostConstruct
	public void blockListener(){
		web3j.blockObservable(true).subscribe(v->{
			EthBlock.Block block = v.getBlock();
			BigInteger blockNumber = block.getNumber();
			
			this.nowBlockNumber = blockNumber;
			log.info("({})当前得到的最新块为:({})",System.currentTimeMillis(),blockNumber);
		});
	}
	 
	/**
	 * transfer 监听
	 */
	public void transcationEventLister(){
		BigInteger maxBlockNumber = transcationEventService.getMaxBlockNumber();
		
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
		Credentials credentials = Credentials.create("6f57d15e205c5b66c1c5a31ac9a2eb3fa5c9b71cd754584f2af00b3336546659");
		
		Small_sol_small small = Small_sol_small.load("0xAe9AC0bfF865dFB27B397C53ea90600cC69a01A5", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		
		small.transferEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
			new Thread(RecordEventHandler.builder()
					.transcationEvent(TranscationEvent.builder()
							.blockHash(v.log.getBlockHash())
							.blockNumber(Long.valueOf(v.log.getBlockNumber().intValue()))
							.createDate(new Date())
							.fromPerson(v._from)
							.toPerson(v._to)
							.nums(v._value.longValue())
							.isCall((short)0)
							.transcationHash(v.log.getTransactionHash())
							.eventName("Transfer")
							.status((short)0)
							.build())
					.transcationEventService(transcationEventService)
					.build()).start();
		});
	}
	
	
	/**
	 * redeem 监听
	 */
	public void redeemEventLister(){
		BigInteger maxBlockNumber = transcationEventService.getMaxBlockNumber();
		
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
		Credentials credentials = Credentials.create("6f57d15e205c5b66c1c5a31ac9a2eb3fa5c9b71cd754584f2af00b3336546659");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		
		bank.redeemEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
			new Thread(RecordEventHandler.builder()
					.transcationEvent(TranscationEvent.builder()
							.blockHash(v.log.getBlockHash())
							.blockNumber(Long.valueOf(v.log.getBlockNumber().intValue()))
							.createDate(new Date())
							.fromPerson(v.sender)
							.toPerson("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73")
							.nums(v.amount.longValue())
							.isCall((short)0)
							.transcationHash(v.log.getTransactionHash())
							.eventName(EventNameEum.REDEEM_EVENT.getName())
							.status((short)0)
							.build())
					.transcationEventService(transcationEventService)
					.build()).start();
		});
	}
	
	/**
	 * withdraw 监听
	 */
	public void withdrawEventLister(){
		BigInteger maxBlockNumber = transcationEventService.getMaxBlockNumber();
		
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
		Credentials credentials = Credentials.create("6f57d15e205c5b66c1c5a31ac9a2eb3fa5c9b71cd754584f2af00b3336546659");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		
		bank.withdrawEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
			new Thread(RecordEventHandler.builder()
					.transcationEvent(TranscationEvent.builder()
							.blockHash(v.log.getBlockHash())
							.blockNumber(Long.valueOf(v.log.getBlockNumber().intValue()))
							.createDate(new Date())
							.fromPerson(v.sender)
							.toPerson("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73")
							.nums(v.amount.longValue())
							.isCall((short)0)
							.transcationHash(v.log.getTransactionHash())
							.eventName(EventNameEum.WITHDRAW_EVENT.getName())
							.status((short)0)
							.build())
					.transcationEventService(transcationEventService)
					.build()).start();
		});
	}
	
	
	
	/**
	 * 确认交易是否成功
	 */
	public void confirmEvent(){
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
							.status((short)1)
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().longValue())
							.confirmBlockNumber(nowBlockNumber.longValue())
							.build(), null);
				} else {
					 ret = transcationEventService.updateTranscationEvent(TranscationEvent.builder()
							.id(v.getId())
							.status((short)2)
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().longValue())
							.confirmBlockNumber(nowBlockNumber.longValue())
							.build(), 1);
				}
				
				if(ret > 0){
					//TODO ret > 0 回调前端
					log.info("回调前端");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
