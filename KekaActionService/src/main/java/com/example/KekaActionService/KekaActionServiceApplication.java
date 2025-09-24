package com.example.KekaActionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KekaActionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KekaActionServiceApplication.class, args);
	}

}
