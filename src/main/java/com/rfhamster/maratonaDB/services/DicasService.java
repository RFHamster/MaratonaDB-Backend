package com.rfhamster.maratonaDB.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.controllers.DicaController;
import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.repositories.DicasRepository;
import com.rfhamster.maratonaDB.repositories.ProblemaRepository;
import com.rfhamster.maratonaDB.vo.CustomMapper;
import com.rfhamster.maratonaDB.vo.DicaInVO;
import com.rfhamster.maratonaDB.vo.DicaVO;

@Service
public class DicasService {
	
	@Autowired
	DicasRepository repository;
	
	@Autowired
	ProblemaRepository problemaRepository;
	
	@Autowired
	PagedResourcesAssembler<DicaVO> assembler;
	
	@Autowired
	UserServices userService;
	
	Long qntPontosDica = 10L;
	
	public DicaVO addDica(DicaInVO data){
		Optional<Problema> p = problemaRepository.findById(data.getProblemaId());
		Problema problema = p.orElseThrow();
		userService.atualizarPontos(data.getUsuario(), qntPontosDica, true);
		Dicas d = salvar(new Dicas(null, data.getUsuario(), data.getConteudo(), data.getProblemaId(), problema));
		return CustomMapper.parseObject(d, DicaVO.class);
	}
	
	public Dicas salvar(Dicas d) {
		return repository.save(d);
	}
	
	public List<Dicas> buscarTodos(){
		return repository.findAll();
	}
	
	public PagedModel<EntityModel<DicaVO>> buscarTodos(Pageable pageable) {
        Page<Dicas> solucoes = repository.findAll(pageable);
        
        var VoPage = solucoes.map(p -> CustomMapper.parseObject(p, DicaVO.class));
        Link link = linkTo(methodOn(DicaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
    }

	public Dicas buscar(Long id) {
		Optional<Dicas> d = repository.findById(id);
		return d.orElse(null);
	}
	public DicaVO buscarRetornoVO(Long id) {
		Optional<Dicas> d = repository.findById(id);
		if(!d.isPresent()) {
			return null;
		}
		return CustomMapper.parseObject(d, DicaVO.class);
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
