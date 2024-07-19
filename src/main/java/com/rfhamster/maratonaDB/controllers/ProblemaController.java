package com.rfhamster.maratonaDB.controllers;

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

import com.rfhamster.maratonaDB.services.ProblemaService;
import com.rfhamster.maratonaDB.vo.ProblemaInsertVO;
import com.rfhamster.maratonaDB.vo.ProblemaVO;

@RestController
@RequestMapping("/problema")
public class ProblemaController {
	@Autowired
	ProblemaService service;
	
	@GetMapping(value = "")
	public ResponseEntity<?> buscarTodos(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit
			) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarTodos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		try {
			ProblemaVO p = service.buscarIdRetornoVO(codigo);
			if(p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Problema nao encontrado");
			}
			return ResponseEntity.ok(p);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> salvarNovoProblema(@ModelAttribute  ProblemaInsertVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		try {
			return ResponseEntity.ok(service.salvar(data));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@DeleteMapping(path = "/{codigo}")
	public ResponseEntity<?> deletar(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Codigo nao encontrado");
		}
	    try {
	        service.deletar(codigo);
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	private boolean checkIfParamsIsNotNull(ProblemaInsertVO data) {
		return data == null || data.getUsuario().isBlank() || data.getTitulo().isBlank()
				 || data.getIdOriginal().isBlank() || data.getOrigem().isBlank()
				 || data.getAssuntos().isEmpty() || data.getProblema() == null
				 || data.getSolucao() == null || data.getFaixa().toString() == ""
				 || data.getConteudoDicas().isEmpty(); 
	}
}
