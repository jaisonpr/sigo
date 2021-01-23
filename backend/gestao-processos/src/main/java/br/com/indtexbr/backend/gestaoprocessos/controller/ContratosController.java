package br.com.indtexbr.backend.gestaoprocessos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.ContratosClient;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosResponse;
import br.com.indtexbr.backend.gestaoprocessos.model.ContratoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gestao-processos/v1/contratos")
public class ContratosController {
	
	@Autowired ContratosClient contratosClient;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContratoDTO>> getAllContratos() {
		log.info("ContratosController.getAllContratos");      
		
		GetAllContratosResponse response = contratosClient.getAllContratos(); 
		
		List<ContratoDTO> contratos = new ArrayList<ContratoDTO>();
				
		/*
		 * for (int index = 0; index < response.getContratos().size(); index++) {
		 * 
		 * 
		 * contratos.add( new ContratoDTO(response.getContratos().get(index).getId(),
		 * response.getContratos().get(index).getTexto(),
		 * response.getContratos().get(index).getArea().name())); }
		 */
        return ResponseEntity.status(HttpStatus.OK).body(contratos);
	}
}
