package br.com.indtexbr.external.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import erp_mock.web_service.Contrato;
import erp_mock.web_service.GetAllContratosRequest;
import erp_mock.web_service.GetAllContratosResponse;
import erp_mock.web_service.GetContratoRequest;
import erp_mock.web_service.GetContratoResponse;

@Endpoint
public class ContratoEndpoint {

	private static final String NAMESPACE_URI = "http://erp-mock/web-service";
	
	private ContratosRepository repository;
	
	@Autowired
	public ContratoEndpoint(ContratosRepository repository) {
		this.repository = repository;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContratoRequest")
	@ResponsePayload
	public GetContratoResponse getContrato(@RequestPayload GetContratoRequest request) {
		GetContratoResponse response = new GetContratoResponse();
		response.setContrato(repository.findContrato( request.getId()));

		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllContratosRequest")
	@ResponsePayload
	public GetAllContratosResponse getContrato(@RequestPayload GetAllContratosRequest request) {
		GetAllContratosResponse response = new GetAllContratosResponse();
		for (Contrato contrato: repository.findAll()) {
			response.getContratos().add(contrato);
		}		
		return response;
	}
}
