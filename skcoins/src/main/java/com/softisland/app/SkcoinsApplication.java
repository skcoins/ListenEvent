package com.softisland.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.softisland.listener.ContractListener;

@MapperScan("com.softisland.mapper")
@ComponentScan("com.softisland")
@SpringBootApplication
public class SkcoinsApplication {
    public static void main(String[] args) {
    	ApplicationContext context = SpringApplication.run(SkcoinsApplication.class, args);
        
        ContractListener contractListener = context.getBean("contractListener",ContractListener.class);
        
        contractListener.transcationEventLister();
        
    }
}
