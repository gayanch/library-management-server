package com.github.gayanch.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class LibraryManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementServerApplication.class, args);
	}

}
