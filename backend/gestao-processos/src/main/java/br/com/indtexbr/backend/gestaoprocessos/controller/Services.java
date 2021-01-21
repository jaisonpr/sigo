package br.com.indtexbr.backend.gestaoprocessos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.ContratosClient;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetContratoResponse;
import br.com.indtexbr.backend.gestaoprocessos.model.ContratoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gestao-processos/v1/contratos")
public class Services {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContratoDTO> getContratos() {
		log.info("Services.getContratos");      
		
		GetContratoResponse response = new ContratosClient().getContrato( 1 ); 
		
		
		ContratoDTO dto = new ContratoDTO();
		
		dto.setId( response.getContrato().getId());
		dto.setTexto( response.getContrato().getTexto());
		dto.setArea( response.getContrato().getArea().name());
		
        return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
}
