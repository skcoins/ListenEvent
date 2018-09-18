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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import com.alibaba.fastjson.JSON;
import com.softisland.config.EventNameEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.contract.Small_sol_small;
import com.softisland.handler.CommonHandler;
import com.softisland.handler.ExchangeCoinsEventHandler;
import com.softisland.handler.RecordEventHandler;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.TranscationEvent;
import com.softisland.service.ExchangeCoinsService;
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
	Bankroll_sol_BankRoll bankroll_sol_BankRoll;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	@Autowired
	ExchangeCoinsService exchangeCoinsService;
	
	@Value("${contract.token-address}")
	String tokenContractAddress;
	
	@Value("${contract.token-send-ower-prikey}")
	String sendOwnerPrikey;
	
	
	
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
		
		Credentials credentials = Credentials.create(sendOwnerPrikey);
		
		Small_sol_small small = Small_sol_small.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
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
		BigInteger maxBlockNumber = exchangeCoinsService.getMaxBlockNumber();
		
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
//		Credentials credentials = Credentials.create(sendOwnerPrikey);
//		
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		//TOKEN 换 积分
		bankroll_sol_BankRoll.redeemEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler<>(v,exchangeCoinsService,EventNameEum.REDEEM_EVENT.getName()));
		});
		
		//积分换TOKEN
		bankroll_sol_BankRoll.withdrawEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler<>(v,exchangeCoinsService,EventNameEum.WITHDRAW_EVENT.getName()));
		});
	}
	
	/**
	 * withdraw 监听
	 */
//	public void withdrawEventLister(){
//		BigInteger maxBlockNumber = transcationEventService.getMaxBlockNumber();
//		
//		if(maxBlockNumber == null){
//			maxBlockNumber = nowBlockNumber;
//		}
//		
//		Credentials credentials = Credentials.create(tokenContractAddress);
//		
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(sendOwnerPrikey, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
//		
//		//开始块
//		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
//		
//		log.info("开始的块信息({})",maxBlockNumber);
//		
//		bank.withdrawEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v->{
//			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler(v,exchangeCoinsService,EventNameEum.WITHDRAW_EVENT.getName()));
//		});
//	}
	
	
	
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
	
	public void confirmExchangeEvent(){
		//通过当前块  查询 交易块+12 < 当前块的
		List<ExchangeCoins> transcationEventList = exchangeCoinsService.queryDefaultTranscationEvent(nowBlockNumber.longValue());
		
		for(ExchangeCoins v : transcationEventList){
			try {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(v.getTranscationHash()).send();
				
				TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
				
				
				int ret = 0 ;
				if(transactionReceipt != null && transactionReceipt.isStatusOK()){
					ret = exchangeCoinsService.updateExchangeCoinsEvent(ExchangeCoins.builder()
							.id(v.getId())
							.status((short)1)
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().longValue())
							.confirmBlockNum(nowBlockNumber.longValue())
							.build(), null);
				} else {
					 ret = exchangeCoinsService.updateExchangeCoinsEvent(ExchangeCoins.builder()
							.id(v.getId())
							.status((short)2)
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().longValue())
							.confirmBlockNum(nowBlockNumber.longValue())
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
	
	public void test() throws IOException{
		EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt("0x13db5dab91440c3b9f00c8c7c3e307e9ee6d72d49c894e853efa924fac4e66d6").send();
		EthGetTransactionReceipt ethGetTransactionReceipt1 = web3j.ethGetTransactionReceipt("0x92f635317aefb47cbbf76046b8667075ee1086c930374a71a50430c97ae6dc45").send();
		
		System.out.println(JSON.toJSONString(ethGetTransactionReceipt.getResult()));
		System.out.println(JSON.toJSONString(ethGetTransactionReceipt1.getResult()));
	}
	
}
