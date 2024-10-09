package com.ict.traveljoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelJoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelJoyApplication.class, args);
	}

}
