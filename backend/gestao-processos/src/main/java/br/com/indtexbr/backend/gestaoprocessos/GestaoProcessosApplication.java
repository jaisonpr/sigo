package br.com.indtexbr.backend.gestaoprocessos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestaoProcessosApplication {
	
	/**
	* Inicia a aplicação Spring boot
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	public static void main(String[] args) {
		SpringApplication.run(GestaoProcessosApplication.class, args);
	}
}
