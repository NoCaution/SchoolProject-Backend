package com.edirnegezgini.visitationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing
public class VisitationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitationServiceApplication.class, args);
	}

}
