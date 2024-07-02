package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.repositories.DicasRepository;

@Service
public class DicasService {
	
	@Autowired
	DicasRepository repository;
	
	//Criar
	public Dicas salvar() {
		return null;
	}
	//Ler
	public Dicas buscar() {
		return null;
	}
	//Atualizar
	public Dicas atualizar() {
		return null;
	}
	//Deletar
	public Dicas deletar() {
		return null;
	}
}
