/**
 * 
 */
package com.softisland.listener;

import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;

import com.softisland.bean.utils.JRedisUtils;
import com.softisland.config.RedisKeyConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service
public class BlockListener {
	
	@Autowired
	Web3j web3j;
	
	@Autowired
	JRedisUtils jRedisUtils;
	

	@PostConstruct
	public void blockListener(){
		web3j.blockObservable(true).subscribe(v->{
			EthBlock.Block block = v.getBlock();
			BigInteger blockNumber = block.getNumber();
			try {
				jRedisUtils.setValue(RedisKeyConfig.BLOCK_NUMBER_NOW, blockNumber.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("({})当前得到的最新块为:({})",System.currentTimeMillis(),blockNumber);
		});
	}
}
