package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.repositories.ProblemaRepository;

@Service
public class ProblemaService {
	
	@Autowired
	ProblemaRepository repository;
	
	//Criar
	public Problema salvar() {
		return null;
	}
	//Ler
	public Problema buscar() {
		return null;
	}
	//Atualizar
	public Problema atualizar() {
		return null;
	}
	//Deletar
	public Problema deletar() {
		return null;
	}
}
