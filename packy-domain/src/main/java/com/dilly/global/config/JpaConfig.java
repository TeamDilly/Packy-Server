package com.dilly.global.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.dilly",
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASPECTJ, pattern = "com.dilly.jwt.*"
	)
)
public class JpaConfig {
}
