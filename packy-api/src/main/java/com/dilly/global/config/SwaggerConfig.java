package com.dilly.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("Packy API")
			.version("1.0.0");

		String jwtSchemeName = "JWT";
		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(jwtSchemeName);
		Components components = new Components()
			.addSecuritySchemes(jwtSchemeName, new SecurityScheme()
				.name(jwtSchemeName)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
			);

		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components)
			.info(info);
	}
}
