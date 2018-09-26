package com.softisland.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.softisland.listener.BankRollContractListener;
import com.softisland.listener.SkCoinContractListener;
import com.softisland.service.ContractService;

@MapperScan("com.softisland.mapper")
@ComponentScan("com.softisland")
@SpringBootApplication
public class SkcoinsApplication {
    public static void main(String[] args) {
    	ApplicationContext context = SpringApplication.run(SkcoinsApplication.class, args);
    	
    	ContractService contractService = context.getBean(ContractService.class);
    	
        BankRollContractListener contractListener = context.getBean(BankRollContractListener.class);
        
        SkCoinContractListener SkCoinContractListener = context.getBean(SkCoinContractListener.class);
        try {
        	
			contractListener.redeemEventLister();
			SkCoinContractListener.ethTokensListener();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
