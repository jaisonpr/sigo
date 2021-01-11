package br.com.indtexbr.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestaoNormasController {

    
    @GetMapping("/gestao-normas")
    public String homePage(Model model) {
 
        return "home";
    }
}
