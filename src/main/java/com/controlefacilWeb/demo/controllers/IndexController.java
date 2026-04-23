package com.controlefacilWeb.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para página inicial
 */
@Controller
public class IndexController {

    /**
     * GET / - Página inicial
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
