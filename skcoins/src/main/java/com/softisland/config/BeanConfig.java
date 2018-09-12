/**
 * 
 */
package com.softisland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

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
	
}
