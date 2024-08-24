package com.projectdev.HotelEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelEaseApplication.class, args);
		System.out.println("Server is running on port number 8080 ...");
	}

}
