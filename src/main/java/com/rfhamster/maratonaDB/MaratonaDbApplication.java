package com.rfhamster.maratonaDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaratonaDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaratonaDbApplication.class, args);
		/*
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("teste");
		System.out.println(result);
		*/
	}

}
