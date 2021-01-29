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

import br.com.indtexbr.frontend.dto.NormaDTO;
import br.com.indtexbr.frontend.helper.WebHelper;
import lombok.extern.slf4j.Slf4j;

/**
* Define os mapeamentos das URIs
*
* @author  jaisonpr
* @version 1.0
* @since   2021-01-24 
*/
@Slf4j
@Controller
public class GestaoNormasController {

	@Value("${api.rest.endpoint.normas}")
	private String URL_NORMAS;
	
	private final HttpServletRequest request;

	@Autowired
	public GestaoNormasController(HttpServletRequest request) {
		this.request = request;
	}
	
	/* Create */
	
	@GetMapping("/gestao-normas/cadastrar")
	public String showFormCadastro(Model model) {
		log.info("GestaoNormasController.showFormCadastro");
		model.addAttribute("norma", new NormaDTO());
		return "normas/cadastro";
	}

	@PostMapping("/gestao-normas/cadastrar")
	public String processFormCadastro(@Valid @ModelAttribute("norma") NormaDTO norma, BindingResult result,
			Model model) {
		log.info("GestaoNormasController.processFormCadastro");

		norma.setAtualizada(Boolean.TRUE);
		norma.setAtiva(Boolean.TRUE);

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(norma);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, WebHelper.getHeaderBearerAuth(request));

			if (norma.getId() != null && norma.getId() > 0) {
				Map<String, String> urlParams = new HashMap<String, String> ();
				urlParams.put("id", norma.getId().toString());
				ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS + "/{id}", HttpMethod.PUT, entity, String.class, urlParams);
				model.addAttribute("successMessage", "Norma alterada com sucesso");
				
			} else {
				ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS, HttpMethod.POST, entity, String.class);
				model.addAttribute("successMessage", "Norma adicionada com sucesso");
			} 
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return showFormListar(model);
	}
		
	/* Read */
	
	@GetMapping("/gestao-normas/")
	public String showFormListar(Model model) {
		log.info("GestaoNormasController.listar");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(URL_NORMAS, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		List<NormaDTO> array = mapper.convertValue(rp.getBody(), new TypeReference<List<NormaDTO>>(){});
		
		model.addAttribute("normas", array);
		if (array.size() == 0) {
			model.addAttribute("infoMessage", "Não exitem normas cadastradas");
		}

		return "normas/lista";
	}
	
	@GetMapping("/gestao-normas/visualizar")
	public String showNorma(@RequestParam("id") String id, Model model) {
		log.info("GestaoNormasController.showNorma ({})", id);

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			NormaDTO dto = mapper.readValue( rp.getBody(), NormaDTO.class);
			
			model.addAttribute("norma", dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "normas/view";
	}
	
	/* Update */
	
	@GetMapping("/gestao-normas/atualizar")
	public String showFormAtualizacao(@RequestParam("id") String id, Model model) {
		log.info("GestaoNormasController.showFormAtualizacao ({})", id);
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			NormaDTO dto = mapper.readValue( rp.getBody(), NormaDTO.class);
			
			model.addAttribute("norma", dto);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "normas/cadastro";
	}

	@PostMapping("/gestao-normas/atualizar")
	public String processFormAtualizacao(@Valid @ModelAttribute("norma") NormaDTO norma, BindingResult result,
			Model model) {
		log.info("GestaoNormasController.processFormAtualizacao");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(norma);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, WebHelper.getHeaderBearerAuth(request));

			ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS, HttpMethod.PUT, entity, String.class);

			model.addAttribute("successMessage", "Norma atualizada com sucesso");

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return showFormListar(model);
	}
	
	
	/* Delete */

	@GetMapping("/gestao-normas/remover")
	public String remover(@RequestParam("id") String id, Model model) {
		log.info("GestaoNormasController.remover ({})", id);
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS + "/{id}", HttpMethod.DELETE, entity, String.class, urlParams);
			
		model.addAttribute("successMessage", "Norma removida com sucesso");
		
		return showFormListar(model);
	}
	
	
}
