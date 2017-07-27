package com.phpdragon.springboot;

import com.phpdragon.springboot.service.MessageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.phpdragon.springboot.db.mapper") //添加对mapper包扫描
//@EnableJms
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class App implements ApplicationRunner {

    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setWebEnvironment(false);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.run(args);
    }
}
