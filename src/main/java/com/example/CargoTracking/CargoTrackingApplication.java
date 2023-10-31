package com.example.CargoTracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CargoTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CargoTrackingApplication.class, args);
	}

}
