package br.com.indtexbr.backend.gestaonormas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.indtexbr.backend.gestaonormas.dto.RequestDTO;
import br.com.indtexbr.backend.gestaonormas.dto.ResponseDTO;
import br.com.indtexbr.backend.gestaonormas.service.NormaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gestao-normas/v1/normas")
public class GestaoNormasController {
	
    @Autowired
    private NormaService normaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ResponseDTO>> getNormas(Pageable pageable) {
		log.info("GestaoNormasController.getNormas");        
		Page<ResponseDTO> normas = normaService.listarNormas(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(normas);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> addNorma(@RequestBody @Valid RequestDTO requestDTO) {
		log.info("GestaoNormasController.adicionarNorma [{}]", requestDTO);		
		ResponseDTO standard = normaService.criarNorma(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(standard);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getNorma(@PathVariable Integer id) {
		log.info("GestaoNormasController.getNormas");        
		ResponseDTO dto = normaService.obterNorma(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ResponseDTO> updateStandard(@PathVariable Integer id, @RequestBody @Valid RequestDTO requestDTO) {
		log.info("GestaoNormasController.updateStandard");        
        ResponseDTO dto = normaService.alterarNorma(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<ResponseDTO> deleteStandard(@PathVariable Integer id) {
		log.info("GestaoNormasController.deleteStandard");        
        normaService.excluirNorma(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
