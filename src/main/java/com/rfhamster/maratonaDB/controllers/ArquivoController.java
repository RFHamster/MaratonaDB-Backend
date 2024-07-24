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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/arquivos/v1")
@Tag(name="Arquivo", description = "Enpoint para recuperação/download de arquivos")
public class ArquivoController {
	@Autowired
	ArquivoService service;
	
	@GetMapping(path = "/{codigo}")
	@Operation(summary = "Buscar um dados de um arquivo", description = "Buscar dados de um arquivo a partir do código fornecido", tags= {"Arquivo"},
			responses = {
			        @ApiResponse(
			            responseCode = "200",
			            description = "Sucesso",
			            content = @Content(schema = @Schema(implementation = ArquivoVO.class))
			        ),
			        @ApiResponse(
			            responseCode = "404",
			            description = "Arquivo não encontrado",
			            content = @Content(schema = @Schema(implementation = String.class))
			        ),
			        @ApiResponse(
			            responseCode = "500",
			            description = "Erro interno do servidor",
			            content = @Content(schema = @Schema(implementation = String.class))
			        )
			    })
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
	@Operation(summary = "Baixar um arquivo", description = "Baixar um arquivo a partir do nome fornecido", tags= {"Arquivo"},
			responses = {
			        @ApiResponse(
			            responseCode = "200",
			            description = "Sucesso",
			            content = @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = Resource.class))
			        ),
			        @ApiResponse(
			            responseCode = "404",
			            description = "Arquivo não encontrado ou corrompido",
			            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
			        ),
			        @ApiResponse(
			            responseCode = "500",
			            description = "Erro interno do servidor",
			            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
			        )
			    })
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
