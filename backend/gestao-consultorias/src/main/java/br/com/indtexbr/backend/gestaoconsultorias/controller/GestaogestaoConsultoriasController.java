package br.com.indtexbr.backend.gestaoconsultorias.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.indtexbr.backend.gestaoconsultorias.dto.ConsultoriaDTO;
import br.com.indtexbr.backend.gestaoconsultorias.service.ConsultoriaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gestao-consultorias/v1/Consultorias")
public class GestaogestaoConsultoriasController {
	
    @Autowired
    private ConsultoriaService ConsultoriaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ConsultoriaDTO>> getConsultorias() {
		log.info("GestaogestaoConsultoriasController.getConsultorias");        
		List<ConsultoriaDTO> Consultorias = ConsultoriaService.listarConsultorias();
        return ResponseEntity.status(HttpStatus.OK).body(Consultorias);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsultoriaDTO> addConsultoria(@RequestBody @Valid ConsultoriaDTO ConsultoriaDTO) {
		log.info("GestaogestaoConsultoriasController.adicionarConsultoria ({})", ConsultoriaDTO);		
		ConsultoriaDTO standard = ConsultoriaService.criarConsultoria(ConsultoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(standard);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsultoriaDTO> getConsultoria(@PathVariable Integer id) {
		log.info("GestaogestaoConsultoriasController.getConsultorias");        
		ConsultoriaDTO dto = ConsultoriaService.obterConsultoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ConsultoriaDTO> updateConsultoria(@PathVariable Integer id, @RequestBody @Valid ConsultoriaDTO ConsultoriaDTO) {
		log.info("GestaogestaoConsultoriasController.updateConsultoria ({})");  
		ConsultoriaDTO.setId(id);
        ConsultoriaDTO dto = ConsultoriaService.alterarConsultoria(ConsultoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ConsultoriaDTO> deleteConsultoria(@PathVariable Integer id) {
		log.info("GestaogestaoConsultoriasController.deleteConsultoria ({})", id);        
        ConsultoriaService.excluirConsultoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
