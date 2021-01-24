package br.com.indtexbr.backend.gestaoprocessos.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ContratoConfiguration {
	
	/**
	* É responsável por controlar o processo de serialização do WSDL
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl");
		return marshaller;
	}
	
	/**
	* Configura o contraClient, define a classe de marshaller
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	@Bean
	public ContratosClient contratoClient(Jaxb2Marshaller marshaller) {
		ContratosClient client = new ContratosClient();
		client.setDefaultUri("http://localhost:9000/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
