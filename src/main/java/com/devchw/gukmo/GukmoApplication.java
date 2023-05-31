package com.devchw.gukmo;

import com.devchw.gukmo.config.BeanNameConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(nameGenerator = BeanNameConfig.class)
public class GukmoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GukmoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GukmoApplication.class, args);
	}
}
