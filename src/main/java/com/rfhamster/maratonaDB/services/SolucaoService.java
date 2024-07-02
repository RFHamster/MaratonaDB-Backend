package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.repositories.SolucaoRepository;

@Service
public class SolucaoService {
	
	@Autowired
	SolucaoRepository repository;
	
	//Criar
	public Solucao salvar() {
		return null;
	}
	//Ler
	public Solucao buscar() {
		return null;
	}
	//Atualizar
	public Solucao atualizar() {
		return null;
	}
	//Deletar
	public Solucao deletar() {
		return null;
	}
}
