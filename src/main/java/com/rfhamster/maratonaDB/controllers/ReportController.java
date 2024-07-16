package com.rfhamster.maratonaDB.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.enums.TipoENUM;
import com.rfhamster.maratonaDB.model.Report;
import com.rfhamster.maratonaDB.services.ReportService;
import com.rfhamster.maratonaDB.vo.ReportVO;

@RestController
@RequestMapping("/reports")
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
	public ResponseEntity< ? > buscarTodos() {
		try {
			return ResponseEntity.ok(reportService.buscarTodos());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/resolvidos")
	public ResponseEntity< ? > buscarTodosResolvidos() {
		try {
			return ResponseEntity.ok(reportService.buscarResolvidos());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/nao-resolvidos")
	public ResponseEntity< ? > buscarTodosNaoResolvidos() {
		try {
			return ResponseEntity.ok(reportService.buscarNaoResolvidos());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/usuario/{username}")
	public ResponseEntity< ? > buscarTodosUsuario(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(reportService.buscarPorUsuario(username));
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
	public ResponseEntity<?> buscarReportProblema() {
		try {
			return ResponseEntity.ok(reportService.buscarReportsProblemas());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(value = "/dica")
	public ResponseEntity<?> buscarReportDica() {
		try {
			return ResponseEntity.ok(reportService.buscarReportsDicas());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/solucao")
	public ResponseEntity<?> buscarReportSolucao() {
		try {
			return ResponseEntity.ok(reportService.buscarReportsSolucoes());
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
	
	@PutMapping(value = "/resolver/{codigo}")
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
