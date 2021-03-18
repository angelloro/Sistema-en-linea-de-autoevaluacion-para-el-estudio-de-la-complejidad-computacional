package com.vaporware.CGS;


import javax.servlet.http.HttpServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CgsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CgsApplication.class, args);
	}

}
