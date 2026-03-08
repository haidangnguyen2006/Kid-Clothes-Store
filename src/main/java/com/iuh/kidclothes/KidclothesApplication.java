package com.iuh.kidclothes;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KidclothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KidclothesApplication.class, args);
	}

}
