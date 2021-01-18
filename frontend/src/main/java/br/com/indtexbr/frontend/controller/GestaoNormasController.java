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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtexbr.frontend.dto.RequestDTO;
import br.com.indtexbr.frontend.dto.ResponseDTO;
import br.com.indtexbr.frontend.helper.WebHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GestaoNormasController {

	@Value("${api.rest.endpoint.normas}") /// gestao-normas/v1/normas
	private String url;

	private final HttpServletRequest request;

	@Autowired
	public GestaoNormasController(HttpServletRequest request) {
		this.request = request;
	}

	@PostMapping("/gestao-normas/cadastrar")
	public String processFormCadastro(@Valid @ModelAttribute("norma") RequestDTO norma, BindingResult result,
			Model model) {
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

	@GetMapping("/gestao-normas/")
	public String showFormListar(Model model) {
		log.info("GestaoNormasController.listar");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

		System.out.println(rp);
		System.out.println("---");
		System.out.println(rp.getBody());
		System.out.println(rp.getBody().getClass());
		System.out.println(rp.getBody().size());

		ObjectMapper mapper = new ObjectMapper();

		List<ResponseDTO> array = mapper.convertValue(rp.getBody(), new TypeReference<List<ResponseDTO>>(){});

		for (ResponseDTO dto : array) {
			System.out.println(dto);
		}
		
		model.addAttribute("normas", array);

		return "normas/lista";
	}
	
	@GetMapping("/gestao-normas/remover")
	public String remover(@RequestParam("id") String id, Model model) {
		model.addAttribute("successMessage", "Norma removida com sucesso");
		System.out.println("removerrrrrrrrrrrrrrrrr");
		System.out.println(id);
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
		
		/*UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("id", id);
		
		System.out.println(builder.buildAndExpand(urlParams).toUri());
		
		
		
		
		String url = "http://test.com/Services/rest/{id}/Identifier";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1234");
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
		uri = UriComponentsBuilder
		        .fromUri(uri)
		        .queryParam("name", "myName")
		        .build()
		        .toUri();*/
		
		
		
		ResponseEntity<String> rp = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, entity, String.class, urlParams);
		
		
		
		return showFormListar(model);
	}
	
	

	@GetMapping("/gestao-normas/listar/")
	public String processFormListar(Model model) {
		log.info("GestaoNormasController.listar");

		try {
			ObjectMapper mapper = new ObjectMapper();
			// String json = mapper.writeValueAsString(norma);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>("body", headers);

			ResponseEntity<String> rp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			System.out.println(rp);
			System.out.println("---");
			System.out.println(rp.getBody());

			model.addAttribute("normasPage", rp);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "normas/lista";
	}

	@GetMapping("/gestao-normas/cadastrar")
	public String showFormCadastro(Model model) {
		log.info("GestaoNormasController.showForm");
		model.addAttribute("norma", new RequestDTO());
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
