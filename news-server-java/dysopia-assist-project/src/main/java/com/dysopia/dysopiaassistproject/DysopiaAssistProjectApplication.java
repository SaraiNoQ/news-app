package com.dysopia.dysopiaassistproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 开启定时任务
@MapperScan("com.dysopia.dysopiaassistproject.*.mapper")
public class DysopiaAssistProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DysopiaAssistProjectApplication.class, args);
    }

}
