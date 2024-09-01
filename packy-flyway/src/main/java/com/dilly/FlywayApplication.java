package com.dilly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@EntityScan(basePackages = {"com.dilly.*"})
@SpringBootApplication
public class FlywayApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name",
            "application-domain, application-flyway");

        ConfigurableApplicationContext ctx = SpringApplication.run(FlywayApplication.class, args);
        ctx.close();
    }
}
