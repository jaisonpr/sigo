package br.com.indtexbr.frontend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtexbr.frontend.dto.ConsultoriaDTO;
import br.com.indtexbr.frontend.dto.ContratoDTO;
import br.com.indtexbr.frontend.helper.WebHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GestaoContratosController {

	@Value("${api.rest.endpoint.contratos}")
	private String url;
	
	private final HttpServletRequest request;

	@Autowired
	public GestaoContratosController(HttpServletRequest request) {
		this.request = request;
	}
		
	/* Create */
	
	@GetMapping("/gestao-contratos/cadastrar")
	public String showFormCadastro(Model model) {
		log.info("GestaoConsultoriasController.showFormCadastro");
		model.addAttribute("contrato", new ContratoDTO());
		return "contratos/cadastro";
	}

	@PostMapping("/gestao-contratos/cadastrar")
	public String processFormCadastro(@Valid @ModelAttribute("contrato") ContratoDTO contrato, BindingResult result,
			Model model) {
		log.info("GestaoConsultoriasController.processFormCadastro");
		/*
		 * if (result.hasErrors()) { System.out.println("\t--> ERRRORRR"); return
		 * "add-user"; }
		 */
		
		try {
			
			System.out.println(contrato);	
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return showFormListar(model);
	}
	
	/* Read */
	
	@GetMapping("/gestao-contratos/")
	public String showFormListar(Model model) {
		log.info("GestaoContratosController.contratos");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		List<ContratoDTO> array = mapper.convertValue(rp.getBody(), new TypeReference<List<ContratoDTO>>(){});
		
		model.addAttribute("consultorias", array);

		return "contratos/lista";
	}
}
