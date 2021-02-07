package com.vaporware.CGS;

import com.vaporware.GeneradorComplejidad.servGenerador;
import javax.servlet.http.HttpServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CgsApplication {
     @Bean	
   public ServletRegistrationBean<HttpServlet> generatorServlet() {
	   ServletRegistrationBean<HttpServlet> Bean = new ServletRegistrationBean<>();
	   Bean.setServlet(new servGenerador());
	   Bean.addUrlMappings("/generador1/*");
          /* Bean.addUrlMappings("/generador1/nlog");
           Bean.addUrlMappings("/generador1/Constante");
           Bean.addUrlMappings("/generador1/Logaritmica");
           Bean.addUrlMappings("/generador1/Cuadratica");
           Bean.addUrlMappings("/generador1/Cubica");
           Bean.addUrlMappings("/generador1/Exponencial");*/
           
           
	   Bean.setLoadOnStartup(1);
	   return Bean;
   }

	public static void main(String[] args) {
		SpringApplication.run(CgsApplication.class, args);
	}

}
