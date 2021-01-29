package br.com.indtexbr.backend.gestaoprocessos.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.ContratosClient;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.Contrato;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosResponse;
import br.com.indtexbr.backend.gestaoprocessos.pojo.ContratoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AtualizacaoContratosTask {
	
	@Value("${api.rest.endpoint.contratos}")
	private String URL_GESTAO_CONTRATOS;
		
	@Value("${auth.server.url}")
	private String URL_AUTH;

	/**
	* Retorna os contratos no servidor ERP-SOAP e os envia para o servidor de Gestão de Consultorias - REST
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	@Autowired ContratosClient contratosClient;
	@Scheduled(fixedDelayString = "${task.contrato.fixedDelay.in.milliseconds}")
	public void verifcarListaContratos() {
		log.info("AtualizacaoContratosTask.verifcarListaContratos");
		
		GetAllContratosResponse response = contratosClient.getAllContratos(); 
		
		List<Contrato> contratos = response.getContratos();
						
		for (Contrato contratoSOAP: contratos) {
			log.info("AtualizacaoContratosTask.verifcarListaContratos : contratoSOAP ({})", contratoSOAP);
			
			//chamar o gestao-consultorias
			ContratoDTO contratoDTO = getContratoDTO( contratoSOAP.getId());
			
			//verificar o id do contrato
			if (contratoDTO == null) {
				log.info("AtualizacaoContratosTask.verifcarListaContratos : 'salvar contrato'", contratoSOAP);
				
				contratoDTO = new ContratoDTO();
				contratoDTO.setIdContratoExterno( contratoSOAP.getId());				
				contratoDTO.setTexto( contratoSOAP.getTexto()); 
				contratoDTO.setArea( contratoSOAP.getArea().value());
				 
				enviarContrato(contratoDTO);
			}
		}
	}
	
	/**
	* Envia para o servidor de Gestão de Consultorias - REST
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	private void enviarContrato(ContratoDTO contratoDTO) {
		log.info("AtualizacaoContratosTask.enviarContrato ({})", contratoDTO);
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(contratoDTO);

			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<String> entity = new HttpEntity<>(json, getHeaderAccessToken());
			
			restTemplate.exchange(URL_GESTAO_CONTRATOS, HttpMethod.POST, entity, String.class);			
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	

	/**
	* Busca os Contratos no módulo de Gestão de Consultorias
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-22 
	*/
	private ContratoDTO getContratoDTO(Integer id) {
		log.info("AtualizacaoContratosTask.getContratoDTO ({})", id);
		log.info("AtualizacaoContratosTask.getContratoDTO ({})", URL_GESTAO_CONTRATOS + "/origem/{id}");
		
		ContratoDTO dto = null;

		Map<String, Integer> urlParams = new HashMap<String, Integer> ();
		urlParams.put("id", id);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("<body>", getHeaderAccessToken());

		ResponseEntity<String> rp = restTemplate.exchange(URL_GESTAO_CONTRATOS + "/origem/{id}", HttpMethod.GET, entity, String.class, urlParams);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue( rp.getBody(), ContratoDTO.class);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}	
		return dto;
	}
	
	/**
	* Retorna o Header com o AccessToken 'setado'
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-20 
	*/
	private HttpHeaders getHeaderAccessToken() {
		log.info("AtualizacaoNormaTask.getHeaderAccessToken");
		
		HttpHeaders headers = new HttpHeaders();
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> responseToken;
		try {
			responseToken = Unirest
					.post(URL_AUTH + "/realms/SIGO/protocol/openid-connect/token")
					.header("Content-Type", "application/x-www-form-urlencoded").field("grant_type", "password")
					.field("client_id", "backend-gestao-normas")
					.field("client_secret", "d877e592-9a4f-4b55-a86b-cb7fba8e65ec").field("username", "gestor")
					.field("password", "123").asString();
						
			String accessToken = (responseToken.getBody().split(",")[0]).split(":")[1].replace("\"", "");
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + accessToken);
			
		} catch (UnirestException e) {
			log.error(e.getMessage());
			throw new IllegalStateException(e);
		}
		return headers;
	}
}
