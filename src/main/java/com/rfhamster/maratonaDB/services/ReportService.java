package com.rfhamster.maratonaDB.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.NoSuchElementException;
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
import com.rfhamster.maratonaDB.controllers.ProblemaController;
import com.rfhamster.maratonaDB.controllers.ReportController;
import com.rfhamster.maratonaDB.controllers.SolucaoController;
import com.rfhamster.maratonaDB.enums.TipoENUM;
import com.rfhamster.maratonaDB.model.Report;
import com.rfhamster.maratonaDB.repositories.ReportRepository;
import com.rfhamster.maratonaDB.vo.CustomMapper;
import com.rfhamster.maratonaDB.vo.ReportVO;

@Service
public class ReportService {
	
	@Autowired
	ReportRepository repository;
	@Autowired
	PagedResourcesAssembler<ReportVO> assembler;
	

	public Report salvar(Report r) {
		return repository.save(r);
	}

	public Report buscar(Long id) {
		Optional<Report> r = repository.findById(id);
		return r.orElseThrow();
	}
	
	public PagedModel<EntityModel<ReportVO>> buscarTodos(Pageable pageable) {
		Page<Report> reports = repository.findAll(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarTodos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarResolvidos(Pageable pageable) {
		Page<Report> reports = repository.buscarReportsResolvidos(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarTodosResolvidos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	
	public PagedModel<EntityModel<ReportVO>> buscarNaoResolvidos(Pageable pageable) {
		Page<Report> reports = repository.buscarReportsNaoResolvidos(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarTodosNaoResolvidos(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	
	public PagedModel<EntityModel<ReportVO>> buscarPorUsuario(String user, Pageable pageable) {
		Page<Report> reports = repository.buscarPorUsuario(user, pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarTodosUsuario(user,pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsProblemas(Pageable pageable) {
		Page<Report> reports = repository.buscarReportsProblemas(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportProblema(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsDicas(Pageable pageable) {
		Page<Report> reports = repository.buscarReportsDicas(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportDica(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsSolucoes(Pageable pageable) {
		Page<Report> reports = repository.buscarReportsSolucoes(pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportSolucao(pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsProblemasId(Long id_origem, Pageable pageable) {
		Page<Report> reports = repository.buscarReportsPorOrigemEId(TipoENUM.PROBLEMA,id_origem,pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportProblema(id_origem, pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsDicasId(Long id_origem, Pageable pageable) {
		Page<Report> reports = repository.buscarReportsPorOrigemEId(TipoENUM.DICA,id_origem,pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportDica(id_origem, pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}
	public PagedModel<EntityModel<ReportVO>> buscarReportsSolucoesId(Long id_origem, Pageable pageable) {
		Page<Report> reports = repository.buscarReportsPorOrigemEId(TipoENUM.SOLUCAO,id_origem,pageable);
		var VoPage = reports.map(p -> CustomMapper.parseObject(p, ReportVO.class));
		
        VoPage.map(this::adicionarLinks);
        
        Link link = linkTo(methodOn(ReportController.class).
        		buscarReportSolucao(id_origem, pageable.getPageNumber(), pageable.getPageSize())).withSelfRel();
		return assembler.toModel(VoPage, link);
	}

	public Report atualizar(Long id, Report novoReport) {
		try {
			Report r = buscar(id);
			r.setId_origem(novoReport.getId_origem());
			r.setMensagem(novoReport.getMensagem());
			r.setOrigem(novoReport.getOrigem());
			r.setUsuario(novoReport.getUsuario());
			return repository.save(r);
		} catch (NoSuchElementException e) {
			throw e;
		}
		
	}
	
	public Report resolverReport(Long id) {
		try {
			Report r = buscar(id);
			r.setResolvido(true);
			return repository.save(r);
		} catch (NoSuchElementException e) {
			throw e;
		}
	}
	
	public Boolean deletar(Report r) {
		repository.delete(r);
		return true;
	}
	
	public Boolean deletar(Long id) {
		repository.deleteById(id);
		return true;
	}
	
	private ReportVO adicionarLinks(ReportVO report) {
		report.add(linkTo(methodOn(ReportController.class).buscar(report.getKeyReport())).withSelfRel());
		TipoENUM origem = report.getOrigem();
		Long idOrigem = report.getId_origem();
		switch (origem) {
        case DICA:
        	report.add(linkTo(methodOn(DicaController.class).buscar(idOrigem)).withRel("endpointOrigem"));
            break;
        case PROBLEMA:
        	report.add(linkTo(methodOn(ProblemaController.class).buscar(idOrigem)).withRel("endpointOrigem"));
            break;
        case SOLUCAO:
        	report.add(linkTo(methodOn(SolucaoController.class).buscar(idOrigem)).withRel("endpointOrigem"));
            break;
        default:
            break;
		}
		return report;
	}
}
