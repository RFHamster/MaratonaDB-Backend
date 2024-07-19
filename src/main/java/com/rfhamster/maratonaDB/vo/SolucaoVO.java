package com.rfhamster.maratonaDB.vo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rfhamster.maratonaDB.controllers.ProblemaController;
import com.rfhamster.maratonaDB.controllers.SolucaoController;
import com.rfhamster.maratonaDB.model.Problema;

public class SolucaoVO extends RepresentationModel<SolucaoVO>{
	private Long keySolucao;
	private String usuario;
	private ArquivoVO solucao;
	@JsonIgnore
	private Problema problema;
	
	public SolucaoVO() {}
	
	public SolucaoVO(Long keySolucao, String usuario, Long problemaId, ArquivoVO solucao) {
		this.keySolucao = keySolucao;
		this.usuario = usuario;
		this.solucao = solucao;
	}
	public Long getKeySolucao() {
		return keySolucao;
	}
	public void setKeySolucao(Long keySolucao) {
		this.keySolucao = keySolucao;
		this.add(linkTo(methodOn(SolucaoController.class).buscar(keySolucao)).withSelfRel());
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public ArquivoVO getSolucao() {
		return solucao;
	}
	public void setSolucao(ArquivoVO solucao) {
		this.solucao = solucao;
	}
	
	public Problema getProblema() {
		return problema;
	}
	public void setProblema(Problema problema) {
		this.problema = problema;
		adicionarLinkToProblema(problema.getId());
	}
	public void adicionarLinkToProblema(Long keySolucao) {
		this.add(linkTo(methodOn(ProblemaController.class).buscar(keySolucao)).withRel("problema"));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(keySolucao, problema, solucao, usuario);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolucaoVO other = (SolucaoVO) obj;
		return Objects.equals(keySolucao, other.keySolucao)
				&& Objects.equals(problema, other.problema) && Objects.equals(solucao, other.solucao)
				&& Objects.equals(usuario, other.usuario);
	}
	
	
	
}
