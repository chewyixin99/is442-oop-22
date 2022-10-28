package com.is442.oop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.is442.oop.config.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class OopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OopApplication.class, args);
		System.out.println("The application is running");
	}
	
	@Configuration
	@EnableWebMvc
	public class WebConfig implements WebMvcConfigurer {

		@Override
		//https://stackoverflow.com/questions/35091524/spring-cors-no-access-control-allow-origin-header-is-present
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
				.allowedOrigins("http://localhost:8888")
				.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
				.allowCredentials(true);
		}
	}
}
