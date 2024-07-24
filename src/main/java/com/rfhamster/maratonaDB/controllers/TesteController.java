package com.rfhamster.maratonaDB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/teste/v1")
public class TesteController {
	@GetMapping(path = "")
    public String helloWorld() {
        return "Hello World";
    }
	
	@GetMapping(path = "/user")
    public String helloWorldUser() {
        return "Hello User";
    }
	
	@GetMapping(path = "/admin")
    public String helloWorldADMIN() {
        return "Hello ADM";
    }
}
