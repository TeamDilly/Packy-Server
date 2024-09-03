package com.dilly;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
	@Server(url = "http://localhost:8081", description = "Local Server URL"),
	@Server(url = "https://dev.packyforyou.shop", description = "Dev Server URL")
})
@SpringBootApplication
public class ApiApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		System.setProperty("spring.config.name",
			"application-api, application-domain, application-infra, application-support");

		SpringApplication.run(ApiApplication.class, args);
	}
}
