package com.rfhamster.maratonaDB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API with Java 17 and Spring Boot 3 for MaratonaDB")
						.version("v1")
						.description("API feita para BackEnd do sistema MaratonaDB")
						.termsOfService(null)
						.license(
							new License()
								.name("Apace 2.0")
								.url(null)));
	}
}
