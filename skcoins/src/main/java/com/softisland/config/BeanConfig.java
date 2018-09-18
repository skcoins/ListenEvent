/**
 * 
 */
package com.softisland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.properties.BankRollProperties;

/**
 * @author Administrator
 *
 */
@Configuration
public class BeanConfig {

	@Bean
	public Web3j web3j(){
		return Web3j.build(new HttpService("http://209.97.164.186:8545"));
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
}
