package com.rfhamster.maratonaDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.services.ArquivoService;
import com.rfhamster.maratonaDB.vo.ArquivoVO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {
	@Autowired
	ArquivoService service;
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable String codigo) {
		try {
			ArquivoVO u = service.buscarByIdVO(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/baixar/{filename:.+}")
	public ResponseEntity< ? > loadResource(@PathVariable String filename) {
		try {
			Arquivo a = service.buscarById(filename);
			if(a == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo nao cadastrado");
			}
			Resource resource = service.loadFileAsResource(filename);
			if(resource == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo corrompido ou nao encontrado");
			}
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(a.getFileType()))
					.header(
						HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
}
