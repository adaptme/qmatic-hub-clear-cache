package com.adapt.apischedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.adapt.schedule")
public class APIScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(APIScheduleApplication.class, args);
	}

}
