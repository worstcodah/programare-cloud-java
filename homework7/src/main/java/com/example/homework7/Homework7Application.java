package com.example.homework7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Homework7Application {

	public static void main(String[] args) {
		SpringApplication.run(Homework7Application.class, args);
	}

}
