package com.devchw.gukmo;

import com.devchw.gukmo.config.BeanNameConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(nameGenerator = BeanNameConfig.class)
public class GukmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GukmoApplication.class, args);
	}
}
