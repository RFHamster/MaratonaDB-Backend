package com.rfhamster.maratonaDB.vo;

import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.rfhamster.maratonaDB.enums.FaixasEnum;

public class ProblemaInsertVO {

	private String usuario;
	private String titulo;
	private String idOriginal;
	private String origem;
	private FaixasEnum faixa;
	private String assuntos;
	private MultipartFile problema;
	private MultipartFile solucao;
	private List<String> conteudoDicas;
	
	public ProblemaInsertVO() {
		
	}
	
	public ProblemaInsertVO(String usuario, String titulo, String idOriginal, String origem, String assuntos,
			MultipartFile problema, MultipartFile solucao, List<String> conteudoDicas, FaixasEnum faixa) {
		this.usuario = usuario;
		this.titulo = titulo;
		this.idOriginal = idOriginal;
		this.origem = origem;
		this.assuntos = assuntos;
		this.problema = problema;
		this.solucao = solucao;
		this.conteudoDicas = conteudoDicas;
		this.faixa = faixa;
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
	public MultipartFile getProblema() {
		return problema;
	}
	public void setProblema(MultipartFile problema) {
		this.problema = problema;
	}
	public MultipartFile getSolucao() {
		return solucao;
	}
	public void setSolucao(MultipartFile solucao) {
		this.solucao = solucao;
	}
	public List<String> getConteudoDicas() {
		return conteudoDicas;
	}
	public void setConteudoDicas(List<String> conteudoDicas) {
		this.conteudoDicas = conteudoDicas;
	}

	public FaixasEnum getFaixa() {
		return faixa;
	}

	public void setFaixa(FaixasEnum faixa) {
		this.faixa = faixa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assuntos, conteudoDicas, faixa, idOriginal, origem, problema, solucao, titulo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaInsertVO other = (ProblemaInsertVO) obj;
		return Objects.equals(assuntos, other.assuntos) && Objects.equals(conteudoDicas, other.conteudoDicas)
				&& faixa == other.faixa && Objects.equals(idOriginal, other.idOriginal)
				&& Objects.equals(origem, other.origem) && Objects.equals(problema, other.problema)
				&& Objects.equals(solucao, other.solucao) && Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuario, other.usuario);
	}	
}
