package com.rfhamster.maratonaDB.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.services.SolucaoService;
import com.rfhamster.maratonaDB.vo.SolucaoInVO;
import com.rfhamster.maratonaDB.vo.SolucaoVO;

@RestController
@RequestMapping("/solucao")
public class SolucaoController {
	
	@Autowired
	SolucaoService service;
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario nao encontrado");
		}
		try {
			SolucaoVO s = service.buscarRetornoVO(codigo);
			if(s == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(s);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/")
	public ResponseEntity< ? > buscarTodos(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarTodos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> salvar(@ModelAttribute  SolucaoInVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		try {
			return ResponseEntity.ok(service.addSolucao(data));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Problema nao achado");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/{codigo}")
	public ResponseEntity<?> deletar(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario nao encontrado");
		}
	    try {
	        service.deletar(codigo);
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	private boolean checkIfParamsIsNotNull(SolucaoInVO data) {
		return data == null || data.getUsuario().isBlank() || data.getFile() == null || data.getProblemaId() == null;
	}
}
