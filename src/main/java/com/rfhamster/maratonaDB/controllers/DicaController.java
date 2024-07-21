package com.rfhamster.maratonaDB.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.services.DicasService;
import com.rfhamster.maratonaDB.vo.DicaInVO;
import com.rfhamster.maratonaDB.vo.DicaVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/dica/v1")
@Tag(name="Dica", description = "Enpoint para fazer CRUD de Dicas")
public class DicaController {
	@Autowired
	DicasService service;
	
	@GetMapping(path = "/{codigo}")
	@Operation(summary = "Buscar dica por código",description = "Buscar os dados de uma dica a partir do código fornecido",tags = {"Dica"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = DicaVO.class))
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Código inválido",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Dica não encontrada",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    })
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario nao encontrado");
		}
		try {
			DicaVO d = service.buscarRetornoVO(codigo);
			if(d == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(d);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "")
	@Operation(summary = "Buscar todas as dicas",description = "Buscar todas as dicas com paginação (Objetct List é uma DicasVO)",tags = {"Dica"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagedModel.class)))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    })
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
	
	@PostMapping(value = "")
	@Operation(summary = "Salvar uma nova dica",description = "Salvar uma nova dica com os dados fornecidos",tags = {"Dica"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = DicaVO.class))
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Requisição de cliente inválida",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    })
	public ResponseEntity<?> salvar(@RequestBody  DicaInVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		try {
			return ResponseEntity.ok(service.addDica(data));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Problema nao achado");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/{codigo}")
	@Operation(summary = "Deletar uma dica",description = "Deletar uma dica a partir do código fornecido",tags = {"Dica"},
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Sucesso - Sem conteúdo"
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Código inválido",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    })
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
	
	private boolean checkIfParamsIsNotNull(DicaInVO data) {
		return data == null || data.getUsuario().isBlank() || data.getConteudo().isBlank() || data.getProblemaId() == null;
	}
}
