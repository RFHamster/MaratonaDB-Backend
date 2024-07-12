package com.rfhamster.maratonaDB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.repositories.DicasRepository;

@Service
public class DicasService {
	
	@Autowired
	DicasRepository repository;
	@Autowired
	ProblemaService problemaService;
	@Autowired
	UserServices userService;
	
	Long qntPontosDica = 10L;
	
	public Dicas addDica(Long idProblema, String usuario, String conteudo){
		Problema p = problemaService.buscar(idProblema);
		if(p == null) {
			return null;
		}
		userService.atualizarPontos(p.getUsuario(), qntPontosDica, true);
		Dicas d = new Dicas(null, usuario, conteudo, idProblema, p);
		return salvar(d);
	}
	
	public Dicas salvar(Dicas d) {
		return repository.save(d);
	}
	
	public List<Dicas> buscarTodos(){
		return repository.findAll();
	}

	public Dicas buscar(Long id) {
		Optional<Dicas> d = repository.findById(id);
		return d.orElse(null);
	}

	public Dicas atualizar(Long id, Dicas dicaNova) {
		Optional<Dicas> dicaExistente = repository.findById(id);
		if(!dicaExistente.isPresent()) {
			return null;
		}
		Dicas dica = dicaExistente.get();
		dica.setProblema(dicaNova.getProblema());
		dica.setProblemaId(dicaNova.getProblemaId());
		dica.setConteudo(dicaNova.getConteudo());
		dica.setUsuario(dicaNova.getUsuario());
		return repository.save(dica);
	}

	public Boolean deletar(Long id) {
		Dicas d = buscar(id);
		userService.atualizarPontos(d.getUsuario(), qntPontosDica, false);
		repository.deleteById(id);
		return true;
	}
	
	public Boolean deletar(Dicas d) {
		userService.atualizarPontos(d.getUsuario(), qntPontosDica, false);
		repository.delete(d);
		return true;
	}
}
