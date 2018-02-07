package com.company.customer;

import javax.inject.Named;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
// Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.company.customer")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Named
	static class JerseyConfig extends ResourceConfig {
		public JerseyConfig() {
			this.packages("com.company.customer");
		}
	}
}