package br.com.curso.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.curso.api.model"}) // Ler e crias as tabelas do modelo
@ComponentScan(basePackages = {"br.*"}) // injeçao de dependencia.
@EnableJpaRepositories(basePackages = {"br.com.curso.api.repository"}) // controlar a parte de repository
@EnableTransactionManagement //Controllar as transaçoes.
@EnableWebMvc // habilita o MVC
@RestController // habila o Rest controller , trabalhar com json
@EnableAutoConfiguration // Spring configura o projeto todo.
public class CursospringrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursospringrestapiApplication.class, args);
	}

}
