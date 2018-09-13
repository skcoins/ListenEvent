/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import com.alibaba.fastjson.JSON;
import com.softisland.contract.Small_sol_small;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
//@Service
public class ContractListener {

	@Autowired
	Web3j web3j;
	
	@Autowired
	Web3j web3jInfura;
	
	
	
	/**
	 * 监听并得到最新块
	 */
	public void blockListener(){
		web3j.blockObservable(true).subscribe(v->{
			EthBlock.Block block = v.getBlock();
			BigInteger blockNumber = block.getNumber();
			
			
		});
	}
	
	
	
	@PostConstruct
	public void blockListener1(){
		
		
		
		web3j.blockObservable(true).subscribe(v->{
			
			EthBlock.Block block = v.getBlock();
			
			BigInteger blockNumber = block.getNumber();
			
			final AtomicInteger count = new AtomicInteger(12);
			BigInteger start = block.getNumber().subtract(new BigInteger(count.toString()));
			
			
			DefaultBlockParameter startBlockNumber = DefaultBlockParameter.valueOf(new BigInteger("2967247"));
            DefaultBlockParameter endBlockNumber = DefaultBlockParameter.valueOf(block.getNumber());
			
			
			log.info("监听块得到的块({})",blockNumber);
			
			Credentials credentials = Credentials.create("6f57d15e205c5b66c1c5a31ac9a2eb3fa5c9b71cd754584f2af00b3336546659");
			
			Small_sol_small small = Small_sol_small.load("0xAe9AC0bfF865dFB27B397C53ea90600cC69a01A5", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
			
			
			small.transferEventObservable(startBlockNumber, endBlockNumber).subscribe(transfer->{
				
				
				transfer.log.isRemoved();
				
				transfer.log.getBlockNumber();
				
				try {
					
//					EthTransaction ethGetTransaction = web3j.ethGetTransactionByHash(transfer.log.getTransactionHash()).send();
//					Transaction t = ethGetTransaction.getResult();
//					
//					Block block1 = web3j.ethGetBlockByHash(transfer.log.getBlockHash(), true).send().getResult();
//					
//					log.info("({}),({}),({}),({})",block1.getNumber(),block1.getNumberRaw(),block1.getGasUsed(),block1.getDifficulty());
//					
//					List<TransactionResult>  list = block1.getTransactions();
//					
//					list.forEach(c->{
//						Transaction t1 = (Transaction)c.get();
//						
//						
//						log.info("交易信息({})",JSON.toJSONString(c));
//					});
					
					EthGetTransactionReceipt ethGetTransactionReceipt = web3jInfura.ethGetTransactionReceipt(transfer.log.getTransactionHash()).send();
					
					
					TransactionReceipt transcationReceipt = ethGetTransactionReceipt.getResult();
					
					
					if(transcationReceipt!=null && transcationReceipt.isStatusOK()){
						log.info("区块号:({}), 交易人({}) ,到达人:({}),钱:({}),消耗燃油:({})",transfer.log.getBlockNumber(),transfer._from,transfer._to,transfer._value,transcationReceipt.getGasUsed());
					}else{
						log.info("未得到状态区块号:({}), 交易人({}) ,到达人:({}),钱:({}),消耗燃油:({})",transfer.log.getBlockNumber(),transfer._from,transfer._to,transfer._value,transfer.log.getData());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			});
			
		});
	}
}
