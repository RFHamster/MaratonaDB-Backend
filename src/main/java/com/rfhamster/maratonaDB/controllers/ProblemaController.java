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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.services.ProblemaService;
import com.rfhamster.maratonaDB.vo.ProblemaAttVO;
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
			@RequestParam(value = "limit", defaultValue = "10") Integer limit
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
	
	@GetMapping(path = "/ativos")
	public ResponseEntity< ? > buscarAtivos(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit
			) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarAtivos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/desativados")
	public ResponseEntity< ? > buscarDesativados(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit
			) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarDesativados(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/faixa/{faixa}")
	public ResponseEntity< ? > buscarFaixa(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@PathVariable FaixasEnum faixa) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarFaixa(faixa,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/origem/{origem}")
	public ResponseEntity< ? > buscarOrigem(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@PathVariable String origem) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarOrigem(origem,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/idOrigem/{idOrigem}")
	public ResponseEntity< ? > buscarIdOrigem(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@PathVariable String idOrigem) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarOrigem(idOrigem,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/assunto/{assunto}")
	public ResponseEntity< ? > buscarAssunto(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@PathVariable String assunto) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarAssunto(assunto,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/titulo/{titulo}")
	public ResponseEntity< ? > buscarTitulo(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@PathVariable String titulo) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(service.buscarTitulo(titulo,pageable));
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
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<?> atualizarProblema(@PathVariable Long codigo, @RequestBody ProblemaAttVO pNovo) {
		if (codigo == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		try {
			ProblemaVO p = service.atualizar(codigo, pNovo);
			if(p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Problema nao achado!");
			}
			return ResponseEntity.ok(p);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PatchMapping(value = "arquivo/{codigo}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> atualizarArquivoProblema(@ModelAttribute Long codigo, @ModelAttribute MultipartFile file) {
		if (file == null || codigo == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		try {
			ProblemaVO p = service.atualizar(codigo, file);
			if(p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Problema nao achado!");
			}
			return ResponseEntity.ok(p);
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
	
	
	@PatchMapping(path = "/ativar/{codigo}")
	public ResponseEntity<?> ativarProblema(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Codigo nao encontrado");
		}
	    try {
	        return ResponseEntity.ok(service.ativarProblema(codigo));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@PatchMapping(path = "/desativar/{codigo}")
	public ResponseEntity<?> desativarProblema(@PathVariable Long codigo) {
		if(codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Codigo nao encontrado");
		}
	    try {
	        return ResponseEntity.ok(service.desativarProblema(codigo));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@PostMapping(path = "assunto/{codigo}/{assunto}")
	public ResponseEntity<?> adicionarAssunto(@PathVariable Long codigo, @PathVariable String assunto) {
		if(assunto.isBlank() || codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Codigo nao encontrado");
		}
	    try {
	        if(!service.adicionarAssunto(codigo,assunto)){
	        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Problema nao encontrado ou assunto repetido");
	        }
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@DeleteMapping(path = "assunto/{codigo}/{assunto}")
	public ResponseEntity<?> deletarAssunto(@PathVariable Long codigo, @PathVariable String assunto) {
		if(assunto.isBlank() || codigo == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Codigo nao encontrado");
		}
	    try {
	        service.removerAssunto(codigo,assunto);
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
