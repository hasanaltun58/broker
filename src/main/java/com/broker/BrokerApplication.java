package com.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BrokerApplication {

	public static void main(String[] args) {
        SpringApplication.run(BrokerApplication.class, args);
	}

}
