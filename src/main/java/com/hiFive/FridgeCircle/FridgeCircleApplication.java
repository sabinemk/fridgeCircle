package com.hiFive.FridgeCircle;

import com.hiFive.FridgeCircle.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class FridgeCircleApplication {

	public static void main(String[] args) {

		SpringApplication.run(FridgeCircleApplication.class, args);

		System.out.println("hei spring");

	}


}
