package com.unidad3.articulo_spring;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

/*
*
* 
    @autor "Yesid Martinez"
*
*
*/
@RestController
@Slf4j
public class ControladorInicio {
    @GetMapping("/")
    public String inicio() {
        log.info("Ejecutando el controlador de inicio");
        return "!Bienvenido a la API de Articulos!";
    }

   
}
