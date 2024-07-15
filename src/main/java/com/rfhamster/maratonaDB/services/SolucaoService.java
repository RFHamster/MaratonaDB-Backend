package com.rfhamster.maratonaDB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.repositories.SolucaoRepository;

@Service
public class SolucaoService {
	
	@Autowired
	SolucaoRepository repository;
	@Autowired
	UserServices userService;
	@Autowired
	ArquivoService arquivoService;
	
	Long qntPontosProblema = 60L;
	Long qntPontosDica = 20L;
	Long qntPontosSolucao = 40L;
	
	public Solucao addSolucao(Problema p, String usuario, Arquivo solucao){
		Solucao s = new Solucao(null, usuario, p.getId(), solucao, p);
		userService.atualizarPontos(p.getUsuario(), qntPontosSolucao, true);
		return salvar(s);
	}
	
	public Solucao salvar(Solucao s) {
		return repository.save(s);
	}

	public List<Solucao> buscarTodos(){
		return repository.findAll();
	}
	
	public Solucao buscar(Long id) {
		Optional<Solucao> s = repository.findById(id);
		return s.orElse(null);
	}

	public Solucao atualizar(Long id, Solucao solNova) {
		Optional<Solucao> solucaoExistente = repository.findById(id);
		if(!solucaoExistente.isPresent()) {
			return null;
		}
		Solucao sol = solucaoExistente.get();
		sol.setProblema(solNova.getProblema());
		sol.setProblemaId(solNova.getProblemaId());
		sol.setUsuario(solNova.getUsuario());
		return repository.save(sol);
	}
	
	public Solucao atualizar(Long id, MultipartFile file) {
		Solucao solucaoExistente = buscar(id);
		if(solucaoExistente == null) {
			return null;
		}
		arquivoService.deletar(solucaoExistente.getSolucao());
		solucaoExistente.setSolucao(arquivoService.storeFile(file));
		return solucaoExistente;
	}

	public Boolean deletar(Long id) {
		Solucao s = buscar(id);
		userService.atualizarPontos(s.getUsuario(), qntPontosSolucao, false);
		repository.deleteById(id);
		return true;
	}
	
	public Boolean deletar(Solucao s) {
		userService.atualizarPontos(s.getUsuario(), qntPontosSolucao, false);
		repository.delete(s);
		return true;
	}
}
