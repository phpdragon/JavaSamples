package com.phpdragon.springboot;

import com.phpdragon.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application implements ApplicationRunner {

    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebEnvironment(false);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.run(args);
    }
}
