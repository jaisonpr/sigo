package br.com.indtexbr.frontend.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtexbr.frontend.dto.RequestDTO;
import br.com.indtexbr.frontend.helper.WebHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GestaoNormasController {

	@Value("${api.rest.endpoint.normas}")
	private String url;

	private final HttpServletRequest request;

	@Autowired
	public GestaoNormasController(HttpServletRequest request) {
		this.request = request;
	}

	@GetMapping("/gestao-normas/listar")
	public String listar(Model model) {
		log.info("GestaoNormasController.listar");
		return "normas/lista";
	}

	@GetMapping("/gestao-normas/cadastrar")
	public String showForm(Model model) {
		log.info("GestaoNormasController.showForm");
		model.addAttribute("norma", new RequestDTO());
		return "normas/cadastro";
	}

	@PostMapping("/gestao-normas/cadastrar")
	public String processForm(@Valid @ModelAttribute("norma") RequestDTO norma, BindingResult result, Model model) {
		log.info("GestaoNormasController.processForm");        
		/*
		 * if (result.hasErrors()) { System.out.println("\t--> ERRRORRR"); return
		 * "add-user"; }
		 */

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(norma);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(json, headers);

			ResponseEntity<String> rp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			

			model.addAttribute("successMessage", "Norma adicionada com sucesso");

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "normas/cadastro";
	}
	
	
	

	@GetMapping("/gestao-normas")
	public String homePage(Model model) {
		log.info("GestaoNormasController.homePage");

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);

		System.out.println(result);

		return "home";
	}

	@GetMapping("/gestao-normas-jwt")
	public String gestao(Model model) {

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));

		ResponseEntity<String> rp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(rp.getStatusCode());
		System.out.println(rp.getBody());

		// String result = restTemplate.getForObject(url, String.class);
		// restTemplate.postForObject(url, entity, String.class);

		return "normas/lista";
	}
}
