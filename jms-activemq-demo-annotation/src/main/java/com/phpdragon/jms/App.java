package com.phpdragon.jms;

import com.phpdragon.jms.logic.MsgPushLogic;
import com.phpdragon.jms.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan(basePackages = "com.phpdragon.jms")
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPushLogic.class);

    @Autowired
    private AppService appService;

    /**
     * 程序入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        App application = (App) context.getBean("app");
        application.run(args);
    }

    public void run(String[] args) {
        try {
            appService.run();
        } catch (Exception e) {
            LOGGER.error("App process exception, error:{}", e.getMessage(), e);
            System.exit(0);
        }
    }
}
