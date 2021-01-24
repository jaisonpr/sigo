package br.com.indtexbr.backend.gestaonormas.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	/**
	* Configura o model mapper da aplicacao
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
   @Bean
   public ModelMapper modelMapper() {
      return new ModelMapper();
   }
}