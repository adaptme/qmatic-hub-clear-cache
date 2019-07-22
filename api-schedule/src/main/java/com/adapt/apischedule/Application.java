package com.adapt.apischedule;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "com.adapt.schedule", "com.adapt.rest" })
public class Application {
	// ip address for addng cors filter
	@Value("#{'${cross.domain}'}")
	private String crossDomain;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * Cors filter configuration
	 */
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.setAllowedOrigins(Collections.singletonList(crossDomain));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
