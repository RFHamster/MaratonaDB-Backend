package com.rfhamster.maratonaDB.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Problema> buscarTodos(Pageable pageable) {
		Page<Problema> problema = repository.findAll(pageable);
        return problema;
	}
	
	public List<Problema> buscarAtivos() {
		List<Problema> problema = repository.buscarProblemasAtivos();
        return problema;
	}
	
	public List<Problema> buscarDesativados() {
		List<Problema> problema = repository.buscarProblemasDesativados();
        return problema;
	}
	
	public List<Problema> buscarFaixa(FaixasEnum faixa) {
		List<Problema> problema = repository.buscarPorFaixa(faixa);
        return problema;
	}
	
	public List<Problema> buscarOrigem(String origem) {
		List<Problema> problema = repository.buscarPorOrigem(origem);
        return problema;
	}
	
	public List<Problema> buscarIdOrigem(String Idorigem) {
		List<Problema> problema = repository.buscarIdOrigem(Idorigem);
        return problema;
	}
	
	public List<Problema> buscarAssunto(String assunto) {
		List<Problema> problema = repository.buscarPorAssunto(assunto);
        return problema;
	}
	
	public List<Problema> buscarTitulo(String titulo) {
		List<Problema> problema = repository.buscarPorTitulo(titulo);
        return problema;
	}

	public Problema atualizar(Long id, ProblemaAttVO pNovo) {
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
		return salvar(p);
	}
	
	public Problema atualizar(Long id, MultipartFile file) {
		Problema p = buscar(id);
		if(p == null) {
			return null;
		}
		arquivoService.deletar(p.getProblema());
		p.setProblema(arquivoService.storeFile(file));
		return p;
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
		arquivoService.deletar(p.getProblema());
		repository.delete(p);
		return true;
	}
	
	public Boolean adicionarAssunto(Long id, String assuntoAdicionar) {
		Problema p = buscar(id);
		if(p == null) {
			return false;
		}
		p.setAssuntos(p.getAssuntos() + ", " + assuntoAdicionar);
		salvar(p);
		return true;
	}
	
	public Boolean removerAssunto(Long id, String assuntoRemover) {
		Problema p = buscar(id);
		if(p == null) {
			return false;
		}
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
		salvar(p);
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
	
	public ProblemaVO retornarVOcomLinkTo(Problema u) {
		ProblemaVO problemaVo = CustomMapper.parseObject(u, ProblemaVO.class);
		problemaVo.add(linkTo(methodOn(ProblemaController.class).buscar(problemaVo.getKeyProblema())).withSelfRel());
		return problemaVo;
	}
}
