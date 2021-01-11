package br.com.indtexbr.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String homePage(Model model) {
		return "index";
	}
}
