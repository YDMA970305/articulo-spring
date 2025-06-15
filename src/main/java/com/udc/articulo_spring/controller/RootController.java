package com.udc.articulo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class RootController {

  
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/usuario/login";
    }


    @GetMapping("/menu")
    public String showMainMenu() {
        return "menu"; 
    }
}