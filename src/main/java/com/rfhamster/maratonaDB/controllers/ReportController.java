package com.rfhamster.maratonaDB.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.enums.TipoENUM;
import com.rfhamster.maratonaDB.model.Report;
import com.rfhamster.maratonaDB.services.ReportService;
import com.rfhamster.maratonaDB.vo.ReportVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports/v1")
@Tag(name="Report", description = "Enpoint para fazer CRUD de Report")
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@PostMapping(value = "")
	public ResponseEntity<?> registrarReport(@RequestBody ReportVO r) {
		try {
			Report report = new Report(null, r.getUsuario(), r.getId_origem(),r.getOrigem(),r.getMensagem(),false);
			report = reportService.salvar(report);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@PostMapping(value = "/problema")
	public ResponseEntity<?> registrarReportProblema(@RequestBody ReportVO r) {
		try {
			Report report = new Report(null, r.getUsuario(), r.getId_origem(),TipoENUM.PROBLEMA,r.getMensagem(),false);
			report = reportService.salvar(report);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@PostMapping(value = "/dica")
	public ResponseEntity<?> registrarReportDica(@RequestBody ReportVO r) {
		try {
			Report report = new Report(null, r.getUsuario(), r.getId_origem(),TipoENUM.DICA,r.getMensagem(),false);
			report = reportService.salvar(report);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@PostMapping(value = "/solucao")
	public ResponseEntity<?> registrarReportSolucao(@RequestBody ReportVO r) {
		try {
			Report report = new Report(null, r.getUsuario(), r.getId_origem(),TipoENUM.SOLUCAO,r.getMensagem(),false);
			report = reportService.salvar(report);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@GetMapping(value = "")
	public ResponseEntity< ? > buscarTodos(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarTodos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/resolvidos")
	public ResponseEntity< ? > buscarTodosResolvidos(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarResolvidos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/nao-resolvidos")
	public ResponseEntity< ? > buscarTodosNaoResolvidos(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarNaoResolvidos(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/usuario/{username}")
	public ResponseEntity< ? > buscarTodosUsuario(@PathVariable("username") String username,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarPorUsuario(username,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		try {
			Report r = reportService.buscar(codigo);
			return ResponseEntity.ok(r);
		}catch (NoSuchElementException e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/problema")
	public ResponseEntity<?> buscarReportProblema(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsProblemas(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/dica")
	public ResponseEntity<?> buscarReportDica(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsDicas(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/solucao")
	public ResponseEntity<?> buscarReportSolucao(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsSolucoes(pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/problema/{codigo}")
	public ResponseEntity<?> buscarReportProblema(@PathVariable Long codigo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsProblemasId(codigo,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/dica/{codigo}")
	public ResponseEntity<?> buscarReportDica(@PathVariable Long codigo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsDicasId(codigo,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/solucao/{codigo}")
	public ResponseEntity<?> buscarReportSolucao(@PathVariable Long codigo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			return ResponseEntity.ok(reportService.buscarReportsSolucoesId(codigo,pageable));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<?> atualizarReport(@PathVariable Long codigo, @RequestBody ReportVO r) {
		try {
			Report report = new Report(null, r.getUsuario(), r.getId_origem(),r.getOrigem(),r.getMensagem(),false);
			report = reportService.atualizar(codigo, report);
			return ResponseEntity.ok(report);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nao Existe Nenhum Report com esse ID");
		}
		catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@PatchMapping(value = "/resolver/{codigo}")
	public ResponseEntity<?> resolverReport(@PathVariable Long codigo) {
		try {
			Report report = reportService.resolverReport(codigo);
			return ResponseEntity.ok(report);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nao Existe Nenhum Report com esse ID");
		}
		catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
	}
	
	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<?> deletarReport(@PathVariable Long codigo) {
		if(reportService.deletar(codigo)) {
			return ResponseEntity.ok("Usuario Excluido");
		}
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
	}
	
}
