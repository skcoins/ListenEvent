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
		return Web3j.build(new HttpService("http://localhost:8545"));
	}
	
}
