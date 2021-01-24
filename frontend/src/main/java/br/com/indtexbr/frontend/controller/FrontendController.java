package br.com.indtexbr.frontend.controller;

import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class FrontendController {
	
	@Value("${api.rest.endpoint.normas}")
	private String URL_NORMAS;

	@Value("${api.rest.endpoint.contratos}")
	private String URL_CONTRATOS;
	
	protected HttpServletRequest request;
	
	@Autowired
	public FrontendController(HttpServletRequest request) {
		this.request = request;
	}
		
	@GetMapping("/home")
    public String homePage(Model model) {	
		log.info("FrontendController.homePage");		
        String firstname = WebHelper.getAccessToken(request).getGivenName();		
		model.addAttribute("nomeUsuario", firstname);		
		this.verficarStatus(model);		
        return "home";
    }
	
	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		log.info("FrontendController.logout");
		request.logout();
		return "redirect:/";
	}	
	
	private void verficarStatus(Model model) {
		log.info("FrontendController.verficarStatus");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		ResponseEntity<HashMap> rp = restTemplate.exchange(URL_NORMAS+"/status", HttpMethod.GET, new HttpEntity<>("body", headers), HashMap.class);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Integer> map = mapper.convertValue(rp.getBody(), new TypeReference<HashMap<String, Integer>>(){});
		
		//QuantidadeInativas
		int qtdInativas = map.get("QuantidadeInativas");
		if ( qtdInativas > 0 ) {
			model.addAttribute("normasInativas", (qtdInativas > 1 ? "Existem "+qtdInativas+" Normas inativas": "Existe 1 Norma inativa"));
		} else {
			model.addAttribute("normasInativas", null);
		}
		//QuantidadeDesatualizadas
		int qtdDesatualizadas = map.get("QuantidadeDesatualizadas");
		if ( qtdDesatualizadas > 0 ) {
			model.addAttribute("normasDesatualizadas", 
					(qtdDesatualizadas > 1 ? "Existem "+qtdDesatualizadas+" Normas desatualizadas": "Existe 1 Norma desatualizada"));
		} else {
			model.addAttribute("normasDesatualizadas", null);
		}
		
		//Contratos sem asssociação
		ResponseEntity<Integer> rpContratos = restTemplate.exchange(URL_CONTRATOS+"/novos", HttpMethod.GET, new HttpEntity<>("body", headers), Integer.class);				
		int qtdNovos = Integer.parseInt( rpContratos.getBody().toString());
		if (qtdNovos > 0) {
			model.addAttribute("contratosNovos", (qtdNovos > 1 ? "Existem "+qtdNovos+" Contratos sem associações": "Existe 1 Contrato sem associação"));
		} else {
			model.addAttribute("contratosNovos", null);			
		}		
	}	
}
