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
import org.springframework.web.multipart.MultipartFile;

import com.rfhamster.maratonaDB.controllers.SolucaoController;
import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.repositories.ProblemaRepository;
import com.rfhamster.maratonaDB.repositories.SolucaoRepository;
import com.rfhamster.maratonaDB.vo.CustomMapper;
import com.rfhamster.maratonaDB.vo.SolucaoInVO;
import com.rfhamster.maratonaDB.vo.SolucaoVO;

@Service
public class SolucaoService {
	
	@Autowired
	SolucaoRepository repository;
	@Autowired
	UserServices userService;
	@Autowired
	ArquivoService arquivoService;
	@Autowired
	ProblemaRepository problemaRepository;
	@Autowired
	PagedResourcesAssembler<SolucaoVO> assembler;
	
	Long qntPontosProblema = 60L;
	Long qntPontosDica = 20L;
	Long qntPontosSolucao = 40L;
	
	public SolucaoVO addSolucao(SolucaoInVO sol){
		Optional<Problema> p = problemaRepository.findById(sol.getProblemaId());
		Problema problema = p.orElseThrow();
		Arquivo solucao = arquivoService.storeFile(sol.getFile());
		Solucao s = salvar(new Solucao(null, sol.getUsuario(), sol.getProblemaId(), solucao, problema));
		userService.atualizarPontos(sol.getUsuario(), qntPontosSolucao, true);
		
		return CustomMapper.parseObject(s, SolucaoVO.class);
	}
	
	public Solucao salvar(Solucao s) {
		return repository.save(s);
	}

	public List<Solucao> buscarTodos(){
		return repository.findAll();
	}
	
	public PagedModel<EntityModel<SolucaoVO>> buscarTodos(Pageable pageable) {
        Page<Solucao> solucoes = repository.findAll(pageable);
        
        var VoPage = solucoes.map(p -> CustomMapper.parseObject(p, SolucaoVO.class));
        Link link = linkTo(methodOn(SolucaoController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
    }
	
	public Solucao buscar(Long id) {
		Optional<Solucao> s = repository.findById(id);
		return s.orElse(null);
	}
	
	public SolucaoVO buscarRetornoVO(Long id) {
		Optional<Solucao> s = repository.findById(id);
        if(!s.isPresent()) {
        	return null;
        }
        return CustomMapper.parseObject(s, SolucaoVO.class);
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
		arquivoService.deletar(s.getSolucao());
		repository.deleteById(id);
		return true;
	}
	
	public Boolean deletar(Solucao s) {
		userService.atualizarPontos(s.getUsuario(), qntPontosSolucao, false);
		repository.delete(s);
		arquivoService.deletar(s.getSolucao());
		return true;
	}
}
