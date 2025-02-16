package com.gdg.sssProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SssProjectApplication {

	public static void main(String[] args) {
		System.out.println("VT_API_KEY: " + System.getenv("VT_API_KEY"));
		System.out.println("VT_API_KEY from System.getProperty(): " + System.getProperty("VT_API_KEY"));
		SpringApplication.run(SssProjectApplication.class, args);
	}

}