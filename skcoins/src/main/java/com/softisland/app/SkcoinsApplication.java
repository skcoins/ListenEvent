package com.softisland.app;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.softisland.listener.ContractListener;
import com.softisland.service.ContractService;

@MapperScan("com.softisland.mapper")
@ComponentScan("com.softisland")
@SpringBootApplication
public class SkcoinsApplication {
    public static void main(String[] args) {
    	ApplicationContext context = SpringApplication.run(SkcoinsApplication.class, args);
    	
    	ContractService contractService = context.getBean("contractService",ContractService.class);
    	
//    	List<String> adminstratorList = new ArrayList<String>();
//    	
//    	adminstratorList.add("0x5e7607d8cE07b689cb709fe4998f79f7eF8eE841");
//    	
//    	contractService.updateBankRoolAdministrator(adminstratorList);
    	
//    	List<DataInfo> dataList = new ArrayList<DataInfo>();
//    	
//    	dataList.add(DataInfo.builder()
//    			.address("0x5e7607d8cE07b689cb709fe4998f79f7eF8eE841")
//    			.oldPoints("100")
//    			.newPoints("70")
//    			.build());
//    	dataList.add(DataInfo.builder()
//    			.address("0x6ce16dB2fA30532c5b36e4Bd6972a5C38295AE1A")
//    			.oldPoints("100")
//    			.newPoints("75")
//    			.build());
//    	dataList.add(DataInfo.builder()
//    			.address("0x761E0113172AE5BDBE61C7b226784b899203E50e")
//    			.oldPoints("100")
//    			.newPoints("105")
//    			.build());
//    	dataList.add(DataInfo.builder()
//    			.address("0x2894129F2d0cd8d924b8FFdD982aca1048bbDAF6")
//    			.oldPoints("100")
//    			.newPoints("65")
//    			.build());
//    	dataList.add(DataInfo.builder()
//    			.address("0xF9984B533Aa0d82006a02769f97015Ab53D4911A")
//    			.oldPoints("100")
//    			.newPoints("55")
//    			.build());
//    	
//    	contractService.updateLedger(LedgerDto.builder()
//    			.serialNumber("1")
//    			.dataList(dataList)
//    			.dateTime(System.currentTimeMillis()/1000)
//    			.build());
        
        ContractListener contractListener = context.getBean("contractListener",ContractListener.class);
        

        try {
			contractListener.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
