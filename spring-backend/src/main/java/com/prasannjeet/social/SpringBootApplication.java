package com.prasannjeet.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Profile;

@org.springframework.boot.autoconfigure.SpringBootApplication
@Profile("!test")
public class SpringBootApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
//        LOG.info("Starting Timetable-Core");
//        while (true) {
//            try {
//                SpringApplication.run(SpringBootApplication.class, args);
//                break;
//            } catch (Exception e) {
//                LOG.error("Startup Exception: {}", e.getMessage());
//                LOG.error("Retrying in 5 seconds...");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
    }

}
