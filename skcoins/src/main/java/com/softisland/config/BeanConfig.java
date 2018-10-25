/**
 * 
 */
package com.softisland.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import com.softisland.bean.utils.JRedisUtils;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.contract.Skcoin_sol_Skcoin;
import com.softisland.properties.BankRollProperties;
import com.softisland.properties.SkCoinProperties;

/**
 * @author Administrator
 *
 */
@Configuration
public class BeanConfig {
	
	@Bean
	@Primary
	public RedisProperties redisProperties(){
		RedisProperties redisProperties = new RedisProperties();
		return redisProperties;
	}
	
	@Bean
	public JRedisUtils jRedisUtils(){
		return JRedisUtils.getInstance(redisProperties());
	}

//	@Bean
//	public Web3j web3j(){
//		return Web3j.build(new HttpService("http://209.97.164.186:8545"));
//	}
	
	@Bean
	public Web3j web3j(){
		return Web3j.build(new HttpService("http://172.16.20.151:8545"));
	}
	
	@Bean
	public Web3j web3jInfura(){
		return Web3j.build(new HttpService("https://rinkeby.infura.io/v3/7cb181d2acf24e14b5a873101e16ce8e"));
	}
	
	@Bean
	public BankRollProperties bankRollProperties(){
		return new BankRollProperties();
	}
	
	@Bean
	public Bankroll_sol_BankRoll bankroll_sol_BankRoll(){
		BankRollProperties bankRollProperties = bankRollProperties();
		Credentials credentials = Credentials.create(bankRollProperties.getMasterPrikey());
		return Bankroll_sol_BankRoll.load(bankRollProperties.getContractAddress(), web3j(), credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
	}
	
	@Bean
	public SkCoinProperties  skCoinProperties(){
		return new SkCoinProperties();
	}
	
	@Bean
	public Skcoin_sol_Skcoin skcoin_sol_Skcoin(){
		SkCoinProperties skCoinProperties = skCoinProperties();
		Credentials credentials = Credentials.create(skCoinProperties.getMasterPrikey());
		return Skcoin_sol_Skcoin.load(skCoinProperties.getContractAddress(), web3j(), credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
	}
}
