package br.com.indtexbr.frontend.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.indtexbr.frontend.helper.WebHelper;

@Controller
public class WebController {
	
	protected HttpServletRequest request;
	
	@Autowired
	public WebController(HttpServletRequest request) {
		this.request = request;
	}
		
	@GetMapping("/home")
    public String homePage(Model model) {			
        String firstname = WebHelper.getAccessToken(request).getGivenName();		
		model.addAttribute("nomeUsuario", firstname);
        return "home";
    }
	
	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/";
	}	
	
}
