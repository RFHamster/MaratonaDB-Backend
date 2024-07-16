package com.rfhamster.maratonaDB.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Report;
import com.rfhamster.maratonaDB.repositories.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
	ReportRepository repository;
	

	public Report salvar(Report r) {
		return repository.save(r);
	}

	public Report buscar(Long id) {
		Optional<Report> r = repository.findById(id);
		return r.orElseThrow();
	}
	
	public List<Report> buscarTodos() {
		return repository.findAll();
	}
	public List<Report> buscarResolvidos() {
		return repository.buscarReportsResolvidos();
	}
	public List<Report> buscarNaoResolvidos() {
		return repository.buscarReportsNaoResolvidos();
	}
	public List<Report> buscarPorUsuario(String user) {
		return repository.buscarPorUsuario(user);
	}
	
	public List<Report> buscarReportsProblemas() {
		return repository.buscarReportsProblemas();
	}
	public List<Report> buscarReportsDicas() {
		return repository.buscarReportsDicas();
	}
	public List<Report> buscarReportsSolucoes() {
		return repository.buscarReportsSolucoes();
	}

	public Report atualizar(Long id, Report novoReport) {
		try {
			Report r = buscar(id);
			r.setId_origem(novoReport.getId_origem());
			r.setMensagem(novoReport.getMensagem());
			r.setOrigem(novoReport.getOrigem());
			r.setUsuario(novoReport.getUsuario());
			return repository.save(r);
		} catch (NoSuchElementException e) {
			throw e;
		}
		
	}
	
	public Report resolverReport(Long id) {
		try {
			Report r = buscar(id);
			r.setResolvido(true);
			return repository.save(r);
		} catch (NoSuchElementException e) {
			throw e;
		}
	}
	
	public Boolean deletar(Report r) {
		repository.delete(r);
		return true;
	}
	
	public Boolean deletar(Long id) {
		repository.deleteById(id);
		return true;
	}
}
