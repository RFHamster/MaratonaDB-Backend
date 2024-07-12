package com.rfhamster.maratonaDB.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.exceptions.FileStorageException;
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
	@Autowired
	SolucaoService solucaoService;
	@Autowired
	UserServices userService;
	@Autowired
	DicasService dicaService;
	
	Long qntPontosProblema = 60L;
	Long qntPontosDica = 10L;
	Long qntPontosSolucao = 40L;
	
	public Problema removerAssunto(Long id, String assuntoRemover) {
		Problema p = buscar(id);
		String assunto = p.getAssuntos();
		String[] assuntos = assunto.split(",");
		String assuntoFinal = "";
		
		for(String s : assuntos) {
			if(!s.equals(assunto)) {
				assuntoFinal += s + ",";
			}
		}
		assuntoFinal.subSequence(0, assuntoFinal.length()-1);
		p.setAssuntos(assuntoFinal);
		return salvar(p);
	}

	public Problema salvar(ProblemaInsertVO data) {
		Arquivo arquivoProblema = null;
		Arquivo arquivoSolucao = null;
		try {
			arquivoProblema = arquivoService.storeFile(data.getProblema());
			arquivoSolucao = arquivoService.storeFile(data.getSolucao());
		} catch (FileStorageException e) {
			arquivoService.deletar(arquivoProblema);
			arquivoService.deletar(arquivoSolucao);
			throw e;
		}
		
		Problema p = new Problema(null, data.getUsuario(), data.getTitulo(), data.getIdOriginal(),
				data.getOrigem(), data.getAssuntos(), data.getFaixa(), arquivoProblema, null, null);
		p = repository.save(p);
		
		
		Solucao sol = new Solucao(null, data.getUsuario(),p.getId(),arquivoSolucao, p);
		solucaoService.salvar(sol);
		List<Solucao> solucoes = new ArrayList<>();
		solucoes.add(sol);
		p.setSolucoes(solucoes);
		
		
		List<Dicas> dicas = new ArrayList<>();
		for(String s : data.getConteudoDicas()) {
			Dicas d = new Dicas(null, p.getUsuario(), s, p.getId(), p);
			dicas.add(d);
			dicaService.salvar(d);
		}
		p.setDicas(dicas);
		
		return p;
	}
	
	public Problema salvar(Problema p) {
		return repository.save(p);
	}

	public Problema buscar(Long id) {
		Optional<Problema> problema = repository.findById(id);
        return problema.orElse(null);
	}
	
	public List<Problema> buscarTodos() {
		List<Problema> problema = repository.findAll();
        return problema;
	}

	public Problema atualizar() {
		return null;
	}

	public Boolean deletar(Long id) {
		Problema p = buscar(id);
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema, false);
		for(Dicas d : p.getDicas()) {
			dicaService.deletar(d);
		}
		for(Solucao s : p.getSolucoes()) {
			solucaoService.deletar(s);
		}
		repository.delete(p);
		return true;
	}
	
	public Problema ativarProblema(Long id) {
		Problema p = buscar(id);
		p.setAtivo(true);
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema + qntPontosSolucao + (qntPontosDica * 10), true);
		return salvar(p);
	}
	
	public Problema desativarProblema(Long id) {
		Problema p = buscar(id);
		p.setAtivo(false);
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema - qntPontosSolucao, false);
		return salvar(p);
	}
}
