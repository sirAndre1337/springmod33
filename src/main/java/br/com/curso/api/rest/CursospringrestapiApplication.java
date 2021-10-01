package br.com.curso.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.curso.api.model"}) // Ler e crias as tabelas do modelo
@ComponentScan(basePackages = {"br.*"}) // injeçao de dependencia.
@EnableJpaRepositories(basePackages = {"br.com.curso.api.repository"}) // controlar a parte de repository
@EnableTransactionManagement //Controllar as transaçoes.
@EnableWebMvc // habilita o MVC
@RestController // habila o Rest controller , trabalhar com json
@EnableAutoConfiguration // Spring configura o projeto todo.
public class CursospringrestapiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(CursospringrestapiApplication.class, args);
	}

	/*Mapeamento global que reflete em todo o sistema*/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/usuario/**")
		.allowedOrigins("*")
		.allowedMethods("*"); // Libera o mapeamento de usuario para todas as origens
	}
	
}
