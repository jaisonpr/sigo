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
import br.com.indtexbr.backend.gestaoconsultorias.dto.ContratoDTO;
import br.com.indtexbr.backend.gestaoconsultorias.service.ConsultoriaService;
import br.com.indtexbr.backend.gestaoconsultorias.service.ContratosService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gestao-consultorias/v1/consultorias")
public class GestaoConsultoriasController {
	
    @Autowired
    private ConsultoriaService consultoriaService;
    
    @Autowired
    private ContratosService contratoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ConsultoriaDTO>> getConsultorias() {
		log.info("GestaoConsultoriasController.getConsultorias");    
        return ResponseEntity.status(HttpStatus.OK).body( consultoriaService.listarConsultorias());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsultoriaDTO> addConsultoria(@RequestBody @Valid ConsultoriaDTO ConsultoriaDTO) {
		log.info("GestaoConsultoriasController.adicionarConsultoria ({})", ConsultoriaDTO);	
        return ResponseEntity.status(HttpStatus.CREATED).body( consultoriaService.criarConsultoria(ConsultoriaDTO));
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsultoriaDTO> getConsultoria(@PathVariable Integer id) {
		log.info("GestaoConsultoriasController.getConsultorias");  
        return ResponseEntity.status(HttpStatus.OK).body(consultoriaService.obterConsultoria(id));
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ConsultoriaDTO> updateConsultoria(@PathVariable Integer id, @RequestBody @Valid ConsultoriaDTO consultoriaDTO) {
		log.info("GestaoConsultoriasController.updateConsultoria ({})");  
		consultoriaDTO.setId(id);
        ConsultoriaDTO dto = consultoriaService.alterarConsultoria(consultoriaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ConsultoriaDTO> deleteConsultoria(@PathVariable Integer id) {
		log.info("GestaoConsultoriasController.deleteConsultoria ({})", id);        
        consultoriaService.excluirConsultoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    
    // Contratos
    

    @GetMapping(value = "/contratos/origem/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContratoDTO> getContratoOrigem(@PathVariable Integer id) {
		log.info("GestaoContratosController.getContratoOrigem ({})", id);  
		try {
	        return ResponseEntity.status(HttpStatus.OK).body(contratoService.obterContratoOrigem(id));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
    
	@PostMapping(value = "/contratos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContratoDTO> addContrato(@RequestBody @Valid ContratoDTO contratoDTO) {
		log.info("GestaoContratosController.adicionarContrato ({})", contratoDTO);	
        return ResponseEntity.status(HttpStatus.CREATED).body( contratoService.criarContrato(contratoDTO));
	}
    
	@PutMapping(value = "/contratos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContratoDTO> alterarContrato(@PathVariable Integer id,@RequestBody @Valid ContratoDTO contratoDTO) {
		log.info("GestaoContratosController.alterarContrato ({} {})", id, contratoDTO);	
        return ResponseEntity.status(HttpStatus.OK).body( contratoService.alterarContrato(contratoDTO));
	}
	
	@GetMapping(value = "/contratos/novos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getQtdContratosNovos() {
		log.info("GestaoContratosController.getQtdContratosNovos");    
        return ResponseEntity.status(HttpStatus.OK).body( contratoService.obterQuantidadeNovos());
	}

	@GetMapping(value = "/contratos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContratoDTO> getContrato(@PathVariable Integer id) {
		log.info("GestaoContratosController.getContratos");    
        return ResponseEntity.status(HttpStatus.OK).body( contratoService.obterContrato(id));
	}

	@GetMapping(value = "/contratos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContratoDTO>> getContratos() {
		log.info("GestaoContratosController.getContratos");    
        return ResponseEntity.status(HttpStatus.OK).body( contratoService.listarContratos());
	}
}
