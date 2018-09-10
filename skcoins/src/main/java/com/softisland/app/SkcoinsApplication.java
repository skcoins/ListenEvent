package com.softisland.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.softisland.mapper")
@ComponentScan("com.softisland")
@SpringBootApplication
public class SkcoinsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkcoinsApplication.class, args);
    }
}
