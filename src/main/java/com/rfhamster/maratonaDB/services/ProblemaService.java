package com.rfhamster.maratonaDB.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
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

import com.rfhamster.maratonaDB.controllers.ProblemaController;
import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.exceptions.FileStorageException;
import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.model.Dicas;
import com.rfhamster.maratonaDB.model.Problema;
import com.rfhamster.maratonaDB.model.Solucao;
import com.rfhamster.maratonaDB.repositories.ProblemaRepository;
import com.rfhamster.maratonaDB.vo.CustomMapper;
import com.rfhamster.maratonaDB.vo.ProblemaAttVO;
import com.rfhamster.maratonaDB.vo.ProblemaInsertVO;
import com.rfhamster.maratonaDB.vo.ProblemaVO;

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
	@Autowired
	PagedResourcesAssembler<ProblemaVO> assembler;
	
	Long qntPontosProblema = 60L;
	Long qntPontosDica = 10L;
	Long qntPontosSolucao = 40L;

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
		
		String assunto = "";
		for(String s : data.getAssuntos()) {
			assunto += s + ", ";
		}
		assunto = assunto.substring(0, assunto.length()-2);
		
		Problema p = new Problema(null, data.getUsuario(), data.getTitulo(), data.getIdOriginal(),
				data.getOrigem(), assunto, data.getFaixa(), arquivoProblema, null, null);
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
	
	public ProblemaVO buscarIdRetornoVO(Long id) {
		Optional<Problema> problema = repository.findById(id);
        if(!problema.isPresent()) {
        	return null;
        }
        return retornarVOcomLinkTo(problema.get());
    }
	
	public PagedModel<EntityModel<ProblemaVO>> buscarTodos(Pageable pageable) {
		Page<Problema> problema = repository.findAll(pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarAtivos(Pageable pageable) {
		Page<Problema> problema = repository.buscarProblemasAtivos(pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarDesativados(Pageable pageable) {
		Page<Problema> problema = repository.buscarProblemasDesativados(pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarFaixa(FaixasEnum faixa, Pageable pageable) {
		Page<Problema> problema = repository.buscarPorFaixa(faixa,pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarOrigem(String origem, Pageable pageable) {
		Page<Problema> problema = repository.buscarPorOrigem(origem,pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarIdOrigem(String Idorigem, Pageable pageable) {
		Page<Problema> problema = repository.buscarIdOrigem(Idorigem,pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarAssunto(String assunto, Pageable pageable) {
		Page<Problema> problema = repository.buscarPorAssunto(assunto,pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}
	
	public PagedModel<EntityModel<ProblemaVO>> buscarTitulo(String titulo, Pageable pageable) {
		Page<Problema> problema = repository.buscarPorTitulo(titulo,pageable);
		Page<ProblemaVO> VoPage = problema.map(this::retornarVOcomLinkTo);
		
        Link link = linkTo(methodOn(ProblemaController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage,link);
	}

	public ProblemaVO atualizar(Long id, ProblemaAttVO pNovo) {
		Problema p = buscar(id);
		if(p == null) {
			return null;
		}
		p.setAssuntos(pNovo.getAssuntos());
		p.setUsuario(pNovo.getUsuario());
		p.setTitulo(pNovo.getTitulo());
		p.setFaixa(pNovo.getFaixa());
		p.setIdOriginal(pNovo.getIdOriginal());
		p.setOrigem(pNovo.getOrigem());
		return retornarVOcomLinkTo(salvar(p));
	}
	
	public ProblemaVO atualizar(Long id, MultipartFile file) {
		Problema p = buscar(id);
		if(p == null) {
			return null;
		}
		arquivoService.deletar(p.getProblema());
		p.setProblema(arquivoService.storeFile(file));
		return retornarVOcomLinkTo(p);
	}

	public Boolean deletar(Long id) {
		Problema p = buscar(id);
		if(p == null) {
			return false;
		}
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema, false);
		for(Dicas d : p.getDicas()) {
			dicaService.deletar(d);
		}
		for(Solucao s : p.getSolucoes()) {
			solucaoService.deletar(s);
		}
		repository.delete(p);
		arquivoService.deletar(p.getProblema());
		return true;
	}
	
	public Boolean adicionarAssunto(Long id, String assuntoAdicionar) {
		Problema p = buscar(id);
		if(p == null) {
			return false;
		}
		
		String assunto = p.getAssuntos();
		String[] assuntos = assunto.split(", ");
		String assuntoFinal = "";
		Boolean flag = true;
		
		for(String s : assuntos) {
			if(s.equals(assuntoAdicionar)) {
				flag = false;
			}
			assuntoFinal += s + ", ";
		}
		
		if(flag) {
			p.setAssuntos(assuntoFinal + assuntoAdicionar);
			salvar(p);
		}
		
		return flag;
	}
	
	public Boolean removerAssunto(Long id, String assuntoRemover) {
		Problema p = buscar(id);
		if(p == null) {
			return false;
		}
		String assunto = p.getAssuntos();
		String[] assuntos = assunto.split(", ");
		String assuntoFinal = "";
		
		for(String s : assuntos) {
			if(!s.equals(assuntoRemover)) {
				assuntoFinal += s + ", ";
			}
		}
		p.setAssuntos(assuntoFinal.substring(0, assunto.length()-2));
		salvar(p);
		return true;
	}
	
	public ProblemaVO ativarProblema(Long id) {
		Problema p = buscar(id);
		p.setAtivo(true);
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema + qntPontosSolucao + (qntPontosDica * 10), true);
		return retornarVOcomLinkTo(salvar(p));
	}
	
	public ProblemaVO desativarProblema(Long id) {
		Problema p = buscar(id);
		p.setAtivo(false);
		userService.atualizarPontos(p.getUsuario(), qntPontosProblema - qntPontosSolucao, false);
		return retornarVOcomLinkTo(salvar(p));
	}
	
	public ProblemaVO retornarVOcomLinkTo(Problema u) {
		ProblemaVO problemaVo = CustomMapper.parseObject(u, ProblemaVO.class);
		problemaVo.add(linkTo(methodOn(ProblemaController.class).buscar(problemaVo.getKeyProblema())).withSelfRel());
		return problemaVo;
	}
}
