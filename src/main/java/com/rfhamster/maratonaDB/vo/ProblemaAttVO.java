package com.rfhamster.maratonaDB.vo;

import java.util.Objects;

import com.rfhamster.maratonaDB.enums.FaixasEnum;

public class ProblemaAttVO {
	private Long id;
	private String usuario;
	private String titulo;
	private String idOriginal;
	private String origem;
	private String assuntos;
	private FaixasEnum faixa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getIdOriginal() {
		return idOriginal;
	}
	public void setIdOriginal(String idOriginal) {
		this.idOriginal = idOriginal;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getAssuntos() {
		return assuntos;
	}
	public void setAssuntos(String assuntos) {
		this.assuntos = assuntos;
	}
	public FaixasEnum getFaixa() {
		return faixa;
	}
	public void setFaixa(FaixasEnum faixa) {
		this.faixa = faixa;
	}
	@Override
	public int hashCode() {
		return Objects.hash(assuntos, faixa, id, idOriginal, origem, titulo, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaAttVO other = (ProblemaAttVO) obj;
		return Objects.equals(assuntos, other.assuntos) && faixa == other.faixa && Objects.equals(id, other.id)
				&& Objects.equals(idOriginal, other.idOriginal) && Objects.equals(origem, other.origem)
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
}
