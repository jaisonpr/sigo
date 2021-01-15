package br.com.indtexbr.frontend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

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
		return "normas/lista";
	}
	
	@GetMapping("/gestao-normas/cadastrar")
	public String cadastrar(Model model) {
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

		ResponseEntity<String> rp  = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		

		System.out.println(rp.getStatusCode());
		System.out.println(rp.getBody());
			
		//String result = restTemplate.getForObject(url, String.class);
		//restTemplate.postForObject(url, entity, String.class); 
				
		return "normas/lista";
	}
}
