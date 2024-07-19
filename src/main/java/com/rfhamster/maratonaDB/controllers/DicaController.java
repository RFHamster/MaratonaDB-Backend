package com.rfhamster.maratonaDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.services.DicasService;

public class DicaController {
	@Autowired
	DicasService service;
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		try {
			Dicas p = service.buscar(codigo);
			if(p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(p);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
}
