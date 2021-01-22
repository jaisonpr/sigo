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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtexbr.frontend.dto.ConsultoriaDTO;
import br.com.indtexbr.frontend.helper.WebHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GestaoConsultoriasController {

	@Value("${api.rest.endpoint.consultorias}")
	private String url;
	
	private final HttpServletRequest request;

	@Autowired
	public GestaoConsultoriasController(HttpServletRequest request) {
		this.request = request;
	}
	
	/* Create */
	
	@GetMapping("/gestao-consultorias/cadastrar")
	public String showFormCadastro(Model model) {
		log.info("GestaoConsultoriasController.showFormCadastro");
		model.addAttribute("consultoria", new ConsultoriaDTO());
		return "consultorias/cadastro";
	}

	@PostMapping("/gestao-consultorias/cadastrar")
	public String processFormCadastro(@Valid @ModelAttribute("consultoria") ConsultoriaDTO consultoria, BindingResult result,
			Model model) {
		log.info("GestaoConsultoriasController.processFormCadastro");
		/*
		 * if (result.hasErrors()) { System.out.println("\t--> ERRRORRR"); return
		 * "add-user"; }
		 */
		
		System.out.println(consultoria.getNome());
		System.out.println(consultoria.getArea());

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(consultoria);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, WebHelper.getHeaderBearerAuth(request));

			if (consultoria.getId() != null && consultoria.getId() > 0) {
				Map<String, String> urlParams = new HashMap<String, String> ();
				urlParams.put("id", consultoria.getId().toString());
				ResponseEntity<String> rp = restTemplate.exchange(url + "/{id}", HttpMethod.PUT, entity, String.class, urlParams);
				model.addAttribute("successMessage", "Consultoria alterada com sucesso");
				
			} else {
				ResponseEntity<String> rp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				model.addAttribute("successMessage", "Consultoria adicionada com sucesso");
			} 
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return showFormListar(model);
	}
		
	/* Read */
	
	@GetMapping("/gestao-consultorias/")
	public String showFormListar(Model model) {
		log.info("GestaoConsultoriasController.listar");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		List<ConsultoriaDTO> array = mapper.convertValue(rp.getBody(), new TypeReference<List<ConsultoriaDTO>>(){});
		
		model.addAttribute("consultorias", array);

		return "consultorias/lista";
	}
	
	@GetMapping("/gestao-consultorias/visualizar")
	public String showConsultoria(@RequestParam("id") String id, Model model) {
		log.info("GestaoConsultoriasController.showConsultoria ({})", id);

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(url + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			ConsultoriaDTO dto = mapper.readValue( rp.getBody(), ConsultoriaDTO.class);
			
			model.addAttribute("consultoria", dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "consultorias/view";
	}
	
	/* Update */
	
	@GetMapping("/gestao-consultorias/atualizar")
	public String showFormAtualizacao(@RequestParam("id") String id, Model model) {
		log.info("GestaoConsultoriasController.showFormAtualizacao ({})", id);
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(url + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			ConsultoriaDTO dto = mapper.readValue( rp.getBody(), ConsultoriaDTO.class);
			
			model.addAttribute("consultoria", dto);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "consultorias/cadastro";
	}

	@PostMapping("/gestao-consultorias/atualizar")
	public String processFormAtualizacao(@Valid @ModelAttribute("consultoria") ConsultoriaDTO consultoria, BindingResult result,
			Model model) {
		log.info("GestaoConsultoriasController.processFormAtualizacao");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(consultoria);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, WebHelper.getHeaderBearerAuth(request));

			ResponseEntity<String> rp = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			model.addAttribute("successMessage", "Consultoria atualizada com sucesso");

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return showFormListar(model);
	}
	
	
	/* Delete */

	@GetMapping("/gestao-consultorias/remover")
	public String remover(@RequestParam("id") String id, Model model) {
		log.info("GestaoConsultoriasController.remover ({})", id);
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, entity, String.class, urlParams);
			
		model.addAttribute("successMessage", "Consultoria removida com sucesso");
		
		return showFormListar(model);
	}
	
}
