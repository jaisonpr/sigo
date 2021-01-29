package br.com.indtexbr.backend.gestaonormas.controller;

import java.util.HashMap;
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

import br.com.indtexbr.backend.gestaonormas.dto.NormaDTO;
import br.com.indtexbr.backend.gestaonormas.service.NormaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


/**
* Define os mapeamentos das URIs
*
* @author  jaisonpr
* @version 1.0
* @since   2021-01-24 
*/
@Slf4j
@RestController
@RequestMapping("/gestao-normas/v1/normas")
public class GestaoNormasController {
	
    @Autowired
    private NormaService normaService;

    @ApiOperation(value = "Retorna todas as normas cadastradas", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NormaDTO>> getNormas() {
		log.info("GestaoNormasController.getNormas");        
		List<NormaDTO> normas = normaService.listarNormas();
        return ResponseEntity.status(HttpStatus.OK).body(normas);
	}

    @ApiOperation(value = "Adiciona uma norma no sistema", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NormaDTO> addNorma(@RequestBody @Valid NormaDTO normaDTO) {
		log.info("GestaoNormasController.adicionarNorma ({})", normaDTO);	
        return ResponseEntity.status(HttpStatus.CREATED).body( normaService.criarNorma(normaDTO));
	}

    @ApiOperation(value = "Retorna uma norma específica a partir do ID", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NormaDTO> getNorma(@PathVariable Integer id) {
		log.info("GestaoNormasController.getNormas");    
        return ResponseEntity.status(HttpStatus.OK).body( normaService.obterNorma(id));
	}

    @ApiOperation(value = "Retorna a quantidade de normas desatualizadas e/ou inativas", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, Integer> > getStatusNormas() {
		log.info("GestaoNormasController.getStatusNormas");        
		int qtdDesatualizadas = normaService.obterQuantidadeDesatualizadas();
		int qtdInativas = normaService.obterQuantidadeInativas();
		
		log.info("GestaoNormasController.getStatusNormas : qtdDesatualizadas = {} e qtdInativas = {}", qtdDesatualizadas, qtdInativas); 
		
		HashMap<String, Integer> qtds = new HashMap<String, Integer>();
		qtds.put("QuantidadeInativas", qtdInativas);
		qtds.put("QuantidadeDesatualizadas", qtdDesatualizadas);
		
        return ResponseEntity.status(HttpStatus.OK).body(qtds);
	}

    @ApiOperation(value = "Altera uma norma a partir do JSON e do ID", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<NormaDTO> updateNorma(@PathVariable Integer id, @RequestBody @Valid NormaDTO normaDTO) {
		log.info("GestaoNormasController.updateNorma ({})");  
		normaDTO.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body( normaService.alterarNorma(normaDTO));
    }

    @ApiOperation(value = "Apaga uma norma específica a partir do ID", response = NormaDTO.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<NormaDTO> deleteNorma(@PathVariable Integer id) {
		log.info("GestaoNormasController.deleteNorma ({})", id);        
        normaService.excluirNorma(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
