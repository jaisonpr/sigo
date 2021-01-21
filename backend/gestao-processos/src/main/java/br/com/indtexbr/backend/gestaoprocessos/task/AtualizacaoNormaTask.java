package br.com.indtexbr.backend.gestaoprocessos.task;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.indtexbr.backend.gestaoprocessos.model.NormaDTO;
import br.com.indtexbr.backend.gestaoprocessos.model.NormaMock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AtualizacaoNormaTask {

	@Value("${api.rest.endpoint.normas.mock}")
	private String URL_MOCK_SERVER;

	@Value("${api.rest.endpoint.normas}")
	private String URL_GESTAO_NORMAS;
		
	@Value("${auth.server.url}")
	private String URL_AUTH;
	
	/**
	* Verifica se a Norma está desatualizada ou cancelada no Catálogo de Normas
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-20 
	*/
	@Scheduled(fixedDelay = 10000)
	public void verifcarListaNormas() {
		log.info("AtualizacaoNormaTask.verifcarListaNormas");
		
		List<NormaDTO> normasDTO = getNormasDTO();
		for (NormaDTO norma: normasDTO) {
			
			NormaMock mock = getNormaMock(norma.getOrgao(), norma.getNumero());
			
			if (mock != null) {
				if ( Integer.parseInt(mock.getVersao()) > norma.getVersao()) {
					log.info("DESATUALIZADA");
					
					norma.setAtualizada(false);
					enviarNorma(norma);
				}
				
				if ( norma.getAtiva() && mock.getStatus()[0].equals("Cancelada")) { // ['Em vigor', 'Cancelada']
					log.info("CANCELADA");
					
					norma.setAtiva(false);
					enviarNorma(norma);
				}
			}
		}
	}

	/**
	* Envia a norma alterada para o servidor de Normas (PUT) 
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-20 
	*/
	private void enviarNorma(NormaDTO norma) {
		log.info("AtualizacaoNormaTask.enviarNorma ({})", norma);

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(norma);
	
			RestTemplate restTemplate = new RestTemplate();

			HttpEntity<String> entity = new HttpEntity<String>(json, getHeaderAccessToken());
			
			Map<String, String> urlParams = new HashMap<String, String> ();
			urlParams.put("id", norma.getId().toString());
			restTemplate.exchange(URL_GESTAO_NORMAS + "/{id}", HttpMethod.PUT, entity, String.class, urlParams);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	* Busca a Norma no Catálogo de Normas
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-20 
	*/
	private NormaMock getNormaMock(String orgao, String numero) {
		log.info("AtualizacaoNormaTask.getNormaMock ({}, {})", orgao, numero);
		
		NormaMock normaMock = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			Map<String, String> params = new HashMap<String, String>();
			params.put("orgao", orgao);
			params.put("numero", numero);

			URI uri = UriComponentsBuilder.fromUriString(URL_MOCK_SERVER).buildAndExpand(params).toUri();

			@SuppressWarnings("unchecked")
			List<String> response = restTemplate.getForObject(uri, List.class);

			ObjectMapper mapper = new ObjectMapper();
			List<NormaMock> normas = mapper.convertValue(response, new TypeReference<List<NormaMock>>() {});

			if (normas != null && normas.size() > 0) {
				normaMock = normas.get(0);
			}

			//Para simular o tempo de resposta da consulta
			TimeUnit.SECONDS.sleep(3);

		} catch (InterruptedException ex) {
			log.error("Ran into an error {}", ex);
			throw new IllegalStateException(ex);
		}
		return normaMock;
	}

	/**
	* Busca a Norma no módulo de Gestão de Normas
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-20 
	*/
	private List<NormaDTO> getNormasDTO() {
		log.info("AtualizacaoNormaTask.getNormasDTO");
		
		List<NormaDTO> normasDTO = null;
	
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("<body>", getHeaderAccessToken());

		@SuppressWarnings("rawtypes")
		ResponseEntity<List> rp = restTemplate.exchange(URL_GESTAO_NORMAS, HttpMethod.GET, entity, List.class);

		ObjectMapper mapper = new ObjectMapper();

		normasDTO = mapper.convertValue(rp.getBody(), new TypeReference<List<NormaDTO>>() {});
		
		return normasDTO;
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
