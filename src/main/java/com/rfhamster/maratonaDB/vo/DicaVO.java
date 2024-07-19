package com.rfhamster.maratonaDB.vo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rfhamster.maratonaDB.controllers.DicaController;
import com.rfhamster.maratonaDB.controllers.ProblemaController;
import com.rfhamster.maratonaDB.model.Problema;

public class DicaVO extends RepresentationModel<DicaVO>{
	private Long keyDica;
	private String usuario;
	private String conteudo;
	@JsonIgnore
	private Problema problema;
	
	public DicaVO() {
		
	}
	
	public DicaVO(Long keyDica, String usuario, String conteudo, Problema problema) {
		this.keyDica = keyDica;
		this.usuario = usuario;
		this.conteudo = conteudo;
		this.problema = problema;
	}
	public Long getKeyDica() {
		return keyDica;
	}
	public void setKeyDica(Long keyDica) {
		this.keyDica = keyDica;
		this.add(linkTo(methodOn(DicaController.class).buscar(keyDica)).withSelfRel());
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Problema getProblema() {
		return problema;
	}
	public void setProblema(Problema problema) {
		this.problema = problema;
		adicionarLinkToProblema(problema.getId());
	}
	public void adicionarLinkToProblema(Long keyDica) {
		this.add(linkTo(methodOn(ProblemaController.class).buscar(keyDica)).withRel("problema"));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(conteudo, keyDica, problema, usuario);
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
		DicaVO other = (DicaVO) obj;
		return Objects.equals(conteudo, other.conteudo) && Objects.equals(keyDica, other.keyDica)
				&& Objects.equals(problema, other.problema) && Objects.equals(usuario, other.usuario);
	}
	
}
