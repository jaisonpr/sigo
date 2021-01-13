package br.com.indtexbr.frontend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GestaoNormasController {
		
	@Value("${api.rest.endpoint}")
	private String url;
	    
    @GetMapping("/gestao-normas")
    public String homePage(Model model) {
        log.info("GestaoNormasController.homePage");
    	
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        System.out.println(result);
    	    	
        return "home";
    }
}
