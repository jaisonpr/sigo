package br.com.indtexbr.backend.gestaoprocessos.consumingwebservice;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosRequest;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetAllContratosResponse;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetContratoRequest;
import br.com.indtexbr.backend.gestaoprocessos.consumingwebservice.wsdl.GetContratoResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContratosClient extends WebServiceGatewaySupport {

	public GetContratoResponse getContrato(Integer id) {

		GetContratoRequest request = new GetContratoRequest();
		request.setId(id);

		log.info("Requesting location for " + id);

		GetContratoResponse response = (GetContratoResponse) getWebServiceTemplate().marshalSendAndReceive(
				"http://localhost:9000/ws/contratos", request,
				new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetContratoRequest"));

		return response;
	}

	public GetAllContratosResponse getAllContratos() {
		log.info("getAllContratos ");

		GetAllContratosRequest request = new GetAllContratosRequest();

		GetAllContratosResponse response = (GetAllContratosResponse) getWebServiceTemplate().marshalSendAndReceive(
				"http://localhost:9000/ws/contratos", request,
				new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetAllContratosRequest"));

		return response;
	}
}
