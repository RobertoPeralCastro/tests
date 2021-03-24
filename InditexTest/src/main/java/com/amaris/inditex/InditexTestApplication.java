package com.amaris.inditex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "inditex.repositories")
@EntityScan(basePackages = "inditex.entities")
public class InditexTestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(InditexTestApplication.class, args);
	}
}
