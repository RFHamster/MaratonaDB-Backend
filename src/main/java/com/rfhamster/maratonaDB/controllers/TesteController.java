package com.rfhamster.maratonaDB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/teste/v1")
public class TesteController {
	@GetMapping
    public String helloWorld() {
        return "Hello World";
    }
}
