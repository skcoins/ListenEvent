/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

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
	
	public void blockListener(){
		
		web3j.blockObservable(true).subscribe(v->{
			
			BigInteger blockNumber = v.getBlock().getNumber();
			
			log.info("监听块得到的块({})",blockNumber);
			
			
		});
	}
}
