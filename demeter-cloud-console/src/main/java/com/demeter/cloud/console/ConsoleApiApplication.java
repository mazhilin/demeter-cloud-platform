package com.demeter.cloud.console;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>封装Dcloud项目DemeterConsoleApiApplication类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 21:02
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@SpringBootApplication(scanBasePackages = {"com.demeter.cloud.model", "com.demeter.cloud.core", "com.demeter.cloud.console"})
@MapperScan({"com.demeter.cloud.model.mapper"})
@EnableTransactionManagement
@EnableScheduling
public class ConsoleApiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConsoleApiApplication.class, args);
        applicationContext.registerShutdownHook();
        applicationContext.start();
    }
}
