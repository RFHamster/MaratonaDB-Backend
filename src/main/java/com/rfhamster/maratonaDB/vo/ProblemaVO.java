package com.rfhamster.maratonaDB.vo;

import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.rfhamster.maratonaDB.enums.FaixasEnum;

public class ProblemaVO extends RepresentationModel<ProblemaVO>{
	private Long keyProblema;
	private String usuario;
	private String titulo;
	private String idOriginal;
	private String origem;
	private String assuntos;
	private FaixasEnum faixa;
	private ArquivoVO problema;
	private List<DicaVO> dicas;
	private List<SolucaoVO> solucoes;
	
	public Long getKeyProblema() {
		return keyProblema;
	}
	public void setKeyProblema(Long keyProblema) {
		this.keyProblema = keyProblema;
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
	public List<DicaVO> getDicas() {
		return dicas;
	}
	public void setDicas(List<DicaVO> dicas) {
		this.dicas = dicas;
	}
	public List<SolucaoVO> getSolucoes() {
		return solucoes;
	}
	public void setSolucoes(List<SolucaoVO> solucoes) {
		this.solucoes = solucoes;
	}
	public ArquivoVO getProblema() {
		return problema;
	}
	public void setProblema(ArquivoVO problema) {
		this.problema = problema;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(assuntos, dicas, faixa, idOriginal, keyProblema, origem,
				problema, solucoes, titulo, usuario);
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
		ProblemaVO other = (ProblemaVO) obj;
		return Objects.equals(assuntos, other.assuntos) && Objects.equals(dicas, other.dicas)
				&& faixa == other.faixa
				&& Objects.equals(idOriginal, other.idOriginal) && Objects.equals(keyProblema, other.keyProblema)
				&& Objects.equals(origem, other.origem) && Objects.equals(problema, other.problema)
				&& Objects.equals(solucoes, other.solucoes) && Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuario, other.usuario);
	}
	
}
