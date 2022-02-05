package com.zyb.zut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// @ComponentScan({"com.zyb", "com.test"})
@ComponentScan("com.zyb")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ZutApplication {

    private static final Logger LOG= (Logger) LoggerFactory.getLogger(ZutApplication.class);
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(ZutApplication.class);
        Environment env= (Environment) app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
