package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.rfhamster.maratonaDB.enums.FaixasEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "problemas")
public class Problema implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "problema_id")
	private Long id;
	
	@Column(name = "usuario_postou")
	private String usuario;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "id_original")
	private String idOriginal;
	
	@Column(name = "origem")
	private String origem;
	
	@Column(name = "assuntos")
	private String assuntos;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nivel_faixa")
	private FaixasEnum faixa;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "arquivo_id", referencedColumnName="arquivo_id")
	private Arquivo problema;
	
	@OneToMany(mappedBy="problema")
	private List<Dicas> dicas;
	
	@OneToMany(mappedBy="problema")
	private List<Solucao> solucoes;
	
	public Problema() {
		
	}

	public Problema(Long id, String usuario, String titulo, String idOriginal, String origem, String assuntos, FaixasEnum faixa,
			Arquivo problema, List<Dicas> dicas, List<Solucao> solucoes) {
		this.id = id;
		this.usuario = usuario;
		this.titulo = titulo;
		this.idOriginal = idOriginal;
		this.origem = origem;
		this.assuntos = assuntos;
		this.faixa = faixa;
		this.problema = problema;
		this.dicas = dicas;
		this.solucoes = solucoes;
		this.ativo = false;
	}

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

	public List<Dicas> getDicas() {
		return dicas;
	}

	public void setDicas(List<Dicas> dicas) {
		this.dicas = dicas;
	}

	public List<Solucao> getSolucoes() {
		return solucoes;
	}

	public void setSolucoes(List<Solucao> solucoes) {
		this.solucoes = solucoes;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Arquivo getProblema() {
		return problema;
	}

	public void setProblema(Arquivo problema) {
		this.problema = problema;
	}

	public FaixasEnum getFaixa() {
		return faixa;
	}

	public void setFaixa(FaixasEnum faixa) {
		this.faixa = faixa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assuntos, ativo, dicas, faixa, id, idOriginal, origem, problema, solucoes, titulo, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problema other = (Problema) obj;
		return Objects.equals(assuntos, other.assuntos) && Objects.equals(ativo, other.ativo)
				&& Objects.equals(dicas, other.dicas) && faixa == other.faixa && Objects.equals(id, other.id)
				&& Objects.equals(idOriginal, other.idOriginal) && Objects.equals(origem, other.origem)
				&& Objects.equals(problema, other.problema) && Objects.equals(solucoes, other.solucoes)
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
}
