package com.dilly.global.config;

import com.dilly.exception.ErrorCode;
import com.dilly.global.response.ErrorResponseDto;
import com.dilly.global.swagger.ApiErrorCodeExample;
import com.dilly.global.swagger.ApiErrorCodeExamples;
import com.dilly.global.swagger.ExampleHolder;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

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

	@Bean
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			ApiErrorCodeExamples apiErrorCodeExamples = handlerMethod.getMethodAnnotation(
				ApiErrorCodeExamples.class);

			if (apiErrorCodeExamples != null) {
				generateErrorCodeResponseExample(operation, apiErrorCodeExamples.value());
			} else {
				ApiErrorCodeExample apiErrorCodeExample = handlerMethod.getMethodAnnotation(
					ApiErrorCodeExample.class);

				if (apiErrorCodeExample != null) {
					generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
				}
			}

			return operation;
		};
	}

	private void generateErrorCodeResponseExample(Operation operation, ErrorCode[] errorCodes) {
		ApiResponses responses = operation.getResponses();

		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
			.map(
				errorCode -> ExampleHolder.builder()
					.holder(getSwaggerExample(errorCode))
					.code(errorCode.getHttpStatus().value())
					.name(errorCode.name())
					.build()
			)
			.collect(Collectors.groupingBy(ExampleHolder::getCode));

		addExamplesToResponses(responses, statusWithExampleHolders);
	}

	private void generateErrorCodeResponseExample(Operation operation, ErrorCode errorCode) {
		ApiResponses responses = operation.getResponses();
		ExampleHolder exampleHolder = ExampleHolder.builder()
			.holder(getSwaggerExample(errorCode))
			.name(errorCode.name())
			.code(errorCode.getHttpStatus().value())
			.build();
		addExamplesToResponses(responses, exampleHolder);
	}

	private Example getSwaggerExample(ErrorCode errorCode) {
		ErrorResponseDto errorResponseDto = ErrorResponseDto.from(errorCode);
		Example example = new Example();
		example.setValue(errorResponseDto);

		return example;
	}

	private void addExamplesToResponses(ApiResponses responses,
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach(
			(status, v) -> {
				Content content = new Content();
				MediaType mediaType = new MediaType();
				ApiResponse apiResponse = new ApiResponse();

				v.forEach(
					exampleHolder -> mediaType.addExamples(
						exampleHolder.getName(),
						exampleHolder.getHolder()
					)
				);
				content.addMediaType("application/json", mediaType);
				apiResponse.setContent(content);
				responses.addApiResponse(String.valueOf(status), apiResponse);
			}
		);
	}

	private void addExamplesToResponses(ApiResponses responses, ExampleHolder exampleHolder) {
		Content content = new Content();
		MediaType mediaType = new MediaType();
		ApiResponse apiResponse = new ApiResponse();

		mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder());
		content.addMediaType("application/json", mediaType);
		apiResponse.content(content);
		responses.addApiResponse(String.valueOf(exampleHolder.getCode()), apiResponse);
	}

}
