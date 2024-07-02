package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Report;
import com.rfhamster.maratonaDB.repositories.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
	ReportRepository repository;
	
	//Criar
	public Report salvar() {
		return null;
	}
	//Ler
	public Report buscar() {
		return null;
	}
	//Atualizar
	public Report atualizar() {
		return null;
	}
	//Deletar
	public Report deletar() {
		return null;
	}
}
