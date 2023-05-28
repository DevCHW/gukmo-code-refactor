package com.devchw.gukmo;

import org.apache.catalina.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class GukmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GukmoApplication.class, args);
	}
}
