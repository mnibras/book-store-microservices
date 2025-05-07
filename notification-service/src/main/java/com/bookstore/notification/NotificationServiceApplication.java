package com.bookstore.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NotificationServiceApplication {

	public static void main(String[] args) {
		System.out.println();
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
