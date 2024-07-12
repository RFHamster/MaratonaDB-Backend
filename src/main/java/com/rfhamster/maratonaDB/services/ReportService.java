package com.rfhamster.maratonaDB.services;

import java.util.List;
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
		return r.orElse(null);
	}
	
	public List<Report> buscarTodos() {
		return repository.findAll();
	}

	public Report atualizar(Long id, Report novoReport) {
		Report r = buscar(id);
		if(r == null) {
			return r;
		}
		r.setId_origem(novoReport.getId_origem());
		r.setMensagem(novoReport.getMensagem());
		r.setOrigem(novoReport.getOrigem());
		r.setResolvido(novoReport.getResolvido());
		r.setUsuario(novoReport.getUsuario());
		return repository.save(r);
	}
	
	public Report resolverReport(Long id) {
		Report r = buscar(id);
		if(r == null) {
			return r;
		}
		r.setResolvido(true);
		return repository.save(r);
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
