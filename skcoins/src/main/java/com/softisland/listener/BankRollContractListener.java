/**
 * 
 */
package com.softisland.listener;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
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
import com.softisland.bean.utils.JRedisUtils;
import com.softisland.config.EventNameEum;
import com.softisland.config.RedisKeyConfig;
import com.softisland.config.TrascationStatusEum;
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
public class BankRollContractListener {

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
	
	@Autowired
	JRedisUtils jRedisUtils;
	
	
	BigInteger nowBlockNumber = new BigInteger("2967247");
	
	
	/**
	 * redeem 监听
	 */
	public void redeemEventLister(){
		BigInteger maxBlockNumber = exchangeCoinsService.getMaxBlockNumber();
		
		if(maxBlockNumber == null){
			try {
				maxBlockNumber = new BigInteger(jRedisUtils.getValue(RedisKeyConfig.BLOCK_NUMBER_NOW));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(maxBlockNumber == null){
			maxBlockNumber = nowBlockNumber;
		}
		
		//开始块
		DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(maxBlockNumber);
		
		log.info("开始的块信息({})",maxBlockNumber);
		//TOKEN 换 积分
		tokenToPointEvent(startBlockNumber);
		
		//积分换TOKEN
		pointToTokenEvent(startBlockNumber);
		
		//结算
		ledgerRecordEvent(startBlockNumber);
	}
	
	/**
	 * TOKEN 换 积分
	 * @param startBlockNumber
	 */
	public void tokenToPointEvent(DefaultBlockParameter startBlockNumber){
		bankroll_sol_BankRoll.tokenToPointEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v -> {
			log.info("发现TOKEN换积分数据({})",v._id);
			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler<>(v,exchangeCoinsService,EventNameEum.REDEEM_EVENT.getName()));
		},error -> {
			log.error("巡检发生异常({})",error);
//			tokenToPointEvent(startBlockNumber);
		});
	}
	
	/**
	 * 积分换TOKEN
	 * @param startBlockNumber
	 */
	public void pointToTokenEvent(DefaultBlockParameter startBlockNumber){
		//积分换TOKEN
		bankroll_sol_BankRoll.pointToTokenEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v -> {
			log.info("发现积分换TOKEN数据({})",v._id);
			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler<>(v,exchangeCoinsService,EventNameEum.WITHDRAW_EVENT.getName()));
		},error -> {
			log.error("巡检发生异常({})",error);
//			pointToTokenEvent(startBlockNumber);
		});
	}
	
	/**
	 * 结算
	 * @param startBlockNumber
	 */
	public void ledgerRecordEvent(DefaultBlockParameter startBlockNumber){
		//结算
		bankroll_sol_BankRoll.ledgerRecordEventEventObservable(startBlockNumber, DefaultBlockParameterName.LATEST).subscribe(v -> {
			log.info("发现结算数据({})",v._id);
			CommonHandler.commonPool.execute(new ExchangeCoinsEventHandler<>(v,exchangeCoinsService,EventNameEum.LEDGER_RECORD_EVENT.getName()));
		},error -> {
			log.error("巡检发生异常({})",error);
//			ledgerRecordEvent(startBlockNumber);
		});
	}
	
	
	
	public void confirmExchangeEvent(){
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
		List<ExchangeCoins> transcationEventList = exchangeCoinsService.queryDefaultTranscationEvent(nowBlockNumber.longValue());
		
		for(ExchangeCoins v : transcationEventList){
			try {
				EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(v.getTranscationHash()).send();
				
				TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
				
				
				int ret = 0 ;
				if(transactionReceipt != null && transactionReceipt.isStatusOK()){
					ret = exchangeCoinsService.updateExchangeCoinsEvent(ExchangeCoins.builder()
							.id(v.getId())
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().toString())
							.confirmBlockNum(nowBlockNumber.longValue())
							.build(), null);
				} else {
					 ret = exchangeCoinsService.updateExchangeCoinsEvent(ExchangeCoins.builder()
							.id(v.getId())
							.status(TrascationStatusEum.FAIL_STATUS.getStatus().shortValue())
							.updateDate(new Date())
							.gas(transactionReceipt.getGasUsed().toString())
							.confirmBlockNum(nowBlockNumber.longValue())
							.build(), 1);
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
