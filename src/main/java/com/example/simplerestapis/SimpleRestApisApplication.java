package com.example.simplerestapis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SimpleRestApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRestApisApplication.class, args);
	}
}
