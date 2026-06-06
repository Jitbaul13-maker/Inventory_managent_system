package com.inv_managemnt.inv_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InvBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvBackendApplication.class, args);
	}

}
