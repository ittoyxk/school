package com.xk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching// 开启缓存，需要显示的指定
@EnableAsync
@SpringBootApplication
public class SchoolWebApplication extends SpringBootServletInitializer implements CommandLineRunner

{
	public static void main(String[] args) {
		SpringApplication.run(SchoolWebApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception
	{

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SchoolWebApplication.class);
	}
}
