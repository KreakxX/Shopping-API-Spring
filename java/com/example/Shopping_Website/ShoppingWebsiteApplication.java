package com.example.Shopping_Website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoppingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingWebsiteApplication.class, args);
	}

}
 