package br.com.indtexbr.backend.gestaonormas.controller;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class WebController {
	
	private final HttpServletRequest request;
	
	@Autowired
	public WebController(HttpServletRequest request) {
		this.request = request;
	}
	
	private void configCommonAttributes(Model model) {
		model.addAttribute("name", getKeycloakSecurityContext().getIdToken().getGivenName());
	}
	
	/**
	 * The KeycloakSecurityContext provides access to several pieces of information
	 * contained in the security token, such as user profile information.
	 */
	private KeycloakSecurityContext getKeycloakSecurityContext() {
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}
}
