package br.com.indtexbr.frontend.helper;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class WebHelper {

	public static HttpHeaders getHeaderBearerAuth(HttpServletRequest request) {
		KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(context.getTokenString());
		return headers;
	}
	
	public static AccessToken getAccessToken(HttpServletRequest request) {
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();        
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>)token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        return accessToken;
	}	
}
