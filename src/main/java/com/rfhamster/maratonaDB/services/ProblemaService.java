package com.rfhamster.maratonaDB.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.repositories.ProblemaRepository;
import com.rfhamster.maratonaDB.vo.ProblemaInsertVO;

@Service
public class ProblemaService {
	
	@Autowired
	ProblemaRepository repository;
	
	@Autowired
	ArquivoService arquivoService;
	
	//Criar
	public Problema salvar(ProblemaInsertVO data) {
		Arquivo arquivoProblema = arquivoService.salvar(arquivoService.storeFile(data.getProblema()));
		Problema p = new Problema(null, data.getUsuario(), data.getTitulo(), data.getIdOriginal(),
				data.getOrigem(), data.getAssuntos(), arquivoProblema, null, null);
		p = repository.save(p);
		Arquivo arquivoSolucao = arquivoService.salvar(arquivoService.storeFile(data.getSolucao()));
		Solucao s = new Solucao(null, data.getUsuario(),p.getId(),arquivoSolucao, p);
		List<Dicas> dicas = new ArrayList<>();
		List<Solucao> solucoes = new ArrayList<>();
		p.setSolucoes(solucoes);
		solucoes.add(s);
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
