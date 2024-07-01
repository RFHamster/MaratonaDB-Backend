package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.repositories.PessoaRepository;

public class PessoaService {
	
	@Autowired
	PessoaRepository repository;
	
	//Criar
	public Pessoa salvar(Pessoa p) {
		return null;
	}
	//Ler
	public Pessoa buscar() {
		return null;
	}
	//Atualizar
	public Pessoa atualizar() {
		return null;
	}
	//Deletar
	public Pessoa deletar() {
		return null;
	}
}
