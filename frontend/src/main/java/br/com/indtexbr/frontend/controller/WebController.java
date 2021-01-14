package br.com.indtexbr.frontend.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	private final HttpServletRequest request;
	
	@Autowired
	public WebController(HttpServletRequest request) {
		this.request = request;
	}
	
	@GetMapping("/home")
    public String homePage(Model model) {		
        return "home";
    }
	
	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/";
	}	
}
