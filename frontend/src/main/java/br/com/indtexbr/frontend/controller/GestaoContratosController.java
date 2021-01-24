package br.com.indtexbr.frontend.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indtexbr.frontend.dto.ConsultoriaDTO;
import br.com.indtexbr.frontend.dto.ContratoDTO;
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
public class GestaoContratosController {

	@Value("${api.rest.endpoint.contratos}")
	private String URL_CONTRATOS;
	
	@Value("${api.rest.endpoint.normas}")
	private String URL_NORMAS;
	
	@Value("${api.rest.endpoint.consultorias}")
	private String URL_CONSULTORIAS;
		
	
	private final HttpServletRequest request;

	@Autowired
	public GestaoContratosController(HttpServletRequest request) {
		this.request = request;
	}
		
	/* Associar */
	
	@GetMapping("/gestao-contratos/associar")
	public ModelAndView showFormAssociacao(@RequestParam("id") String id, Model model) {
		log.info("GestaoContratosController.showFormAssociacao");
		
		ModelAndView mav = new ModelAndView("contratos/cadastro");
		
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_CONTRATOS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			log.info("GestaoContratosController.showFormAssociacao 'obter contrato' ");

			ObjectMapper mapper = new ObjectMapper();
			ContratoDTO contrato = mapper.readValue( rp.getBody(), ContratoDTO.class);
			
			log.info("GestaoContratosController.showFormAssociacao 'obter lista de consultorias' ");
			
			List<ConsultoriaDTO> listaConsultorias = getConsultorias();
			log.info("GestaoContratosController.showFormAssociacao 'lista de consultorias' {}", listaConsultorias);
			
			log.info("GestaoContratosController.showFormAssociacao 'obter lista de normas' ");
			
			List<NormaDTO> listaNormas = getNormas();
			log.info("GestaoContratosController.showFormAssociacao 'lista de normas' {}", listaNormas);
			
			log.info("GestaoContratosController.showFormAssociacao : {}", contrato); 
			
			model.addAttribute("contrato", contrato);
			
			mav.addObject("listaConsultorias", listaConsultorias);
			mav.addObject("listaNormas", listaNormas);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return mav;
	}

	@PostMapping("/gestao-contratos/cadastrar")
	public String processFormCadastro(@Valid @ModelAttribute("contrato") ContratoDTO contrato, BindingResult result,
			Model model) {
		log.info("GestaoConsultoriasController.processFormCadastro");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(contrato);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, WebHelper.getHeaderBearerAuth(request));

			Map<String, String> urlParams = new HashMap<String, String> ();
			urlParams.put("id", contrato.getId().toString());
			ResponseEntity<String> rp = restTemplate.exchange(URL_CONTRATOS + "/{id}", HttpMethod.PUT, entity, String.class, urlParams);
			
			model.addAttribute("successMessage", "Contrato alterada com sucesso");
					
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
		
		List<ContratoDTO> contratos = getContratos(); 
				
		for (ContratoDTO contratoDTO: contratos) {
			preencherOutrasEntidades(contratoDTO);
		}
		model.addAttribute("contratos", contratos);
		if (contratos.size() == 0) {
			model.addAttribute("infoMessage", "Não exitem Contratos cadastrados");
		}

		return "contratos/lista";
	}
	
	@GetMapping("/gestao-contratos/visualizar")
	public String showContrato(@RequestParam("id") String id, Model model) {
		log.info("GestaoContratosController.showContrato ({})", id);

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id);
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_CONTRATOS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			ContratoDTO dto = mapper.readValue( rp.getBody(), ContratoDTO.class);			
			preencherOutrasEntidades(dto);
			model.addAttribute("contrato", dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("errorMessage", "Ocorreu um erro");
		}
		return "contratos/view";
	}
	
	
	
	
	private ConsultoriaDTO getConsultoria(Integer id) {
		log.info("GestaoContratosController.getConsultoria ({})", id);
		
		ConsultoriaDTO consultoriaDTO = null;
		
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id.toString());
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_CONSULTORIAS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			consultoriaDTO = mapper.readValue( rp.getBody(), ConsultoriaDTO.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return consultoriaDTO;
	}
	
	private List<ContratoDTO> getContratos() {
		log.info("GestaoContratosController.getContratos");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		
		ResponseEntity<List> rp = restTemplate.exchange("http://localhost:8093/gestao-consultorias/v1/consultorias/contratos", HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		List<ContratoDTO> contratos = mapper.convertValue(rp.getBody(), new TypeReference<List<ContratoDTO>>(){});
		
		log.info("GestaoContratosController.getContratos : contratos {}", contratos);
		
		return contratos;
	}
	
	private NormaDTO getNorma(Integer id) {
		log.info("GestaoContratosController.getNorma ({})", id);
		
		NormaDTO norma = null;
		
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = new HttpEntity<>("body", WebHelper.getHeaderBearerAuth(request));
		
		Map<String, String> urlParams = new HashMap<String, String> ();
		urlParams.put("id", id.toString());
					
		ResponseEntity<String> rp = restTemplate.exchange(URL_NORMAS + "/{id}", HttpMethod.GET, entity, String.class, urlParams);
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			norma = mapper.readValue( rp.getBody(), NormaDTO.class);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
		}
		return norma;
	}
	
	private List<ConsultoriaDTO> getConsultorias() {
		log.info("GestaoContratosController.getConsultorias");
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(URL_CONSULTORIAS, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		return mapper.convertValue(rp.getBody(), new TypeReference<List<ConsultoriaDTO>>(){});
	}
	
	private List<NormaDTO> getNormas() {
		log.info("GestaoContratosController.getNormas");
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = WebHelper.getHeaderBearerAuth(request);

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<List> rp = restTemplate.exchange(URL_NORMAS, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		return mapper.convertValue(rp.getBody(), new TypeReference<List<NormaDTO>>(){});
	}
	
	private void preencherOutrasEntidades(ContratoDTO contratoDTO) {

		String normasFormatado = "";	
		if (contratoDTO.getNormas() != null) {			
			for (Integer idNorma: contratoDTO.getNormas()) {
				NormaDTO normaDTO = getNorma(idNorma);
				normasFormatado += normaDTO.getOrgao() +" "+ normaDTO.getNumero();
			}
			contratoDTO.setNormasFormatado(normasFormatado);
		} 
		if (normasFormatado.equals("")) {
			contratoDTO.setNormasFormatado("-- não associado --");
		}
		if (contratoDTO.getIdConsultoria() != null) {
			ConsultoriaDTO consultoriaDTO = getConsultoria(contratoDTO.getIdConsultoria());
			contratoDTO.setConsultoria(consultoriaDTO.getNome());
		} else {
			contratoDTO.setConsultoria("-- não associado --");
		}
	}
}
