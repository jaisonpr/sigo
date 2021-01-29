package br.com.indtexbr.backend.gestaoprocessos.consumingwebservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosRequest;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosResponse;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetContratoRequest;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetContratoResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContratosClient extends WebServiceGatewaySupport {

	@Value("${api.soap.endpoint.contratos.mock}")
	private String URL_EXTERNO;
	
	/**
	* Carrega um Contrato SOAP do ERP-Mock apartir do id
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	public GetContratoResponse getContrato(Integer id) {

		GetContratoRequest request = new GetContratoRequest();
		request.setId(id);

		log.info("Requesting location for " + id);

		GetContratoResponse response = (GetContratoResponse) getWebServiceTemplate().marshalSendAndReceive(
				URL_EXTERNO, request,
				new SoapActionCallback("http://erp-mock/web-service/GetContratoRequest"));

		return response;
	}


	/**
	* Lista todos os Contratos SOAP do ERP-Mock
	*
	* @author  jaisonpr
	* @version 1.0
	* @since   2021-01-24 
	*/
	public GetAllContratosResponse getAllContratos() {
		log.info("getAllContratos ");

		GetAllContratosRequest request = new GetAllContratosRequest();

		GetAllContratosResponse response = (GetAllContratosResponse) getWebServiceTemplate().marshalSendAndReceive(
				URL_EXTERNO, request,
				new SoapActionCallback("http://erp-mock/web-service/GetAllContratosRequest"));

		return response;
	}
}
