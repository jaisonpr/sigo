package br.com.indtexbr.backend.gestaoprocessos.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ContratoConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl");
		return marshaller;
	}

	@Bean
	public ContratosClient contratoClient(Jaxb2Marshaller marshaller) {
		ContratosClient client = new ContratosClient();
		client.setDefaultUri("http://localhost:9000/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
