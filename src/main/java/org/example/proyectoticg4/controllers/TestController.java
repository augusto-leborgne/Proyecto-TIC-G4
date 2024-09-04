package org.example.proyectoticg4.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
