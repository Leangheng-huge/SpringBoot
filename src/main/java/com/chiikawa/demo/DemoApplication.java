package com.chiikawa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	//http://localhost:8080/api/v1/heart
@GetMapping("/api/v1/heart")
	public static String hello() {
		return "Hello World from sping boot";
	}
}
