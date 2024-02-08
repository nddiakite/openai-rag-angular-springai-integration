package fr.dtek.lab;

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
    }
}
