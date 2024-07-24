package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solucoes")
public class Solucao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "solucao_id")
	private Long id;
	
	@Column(name = "usuario_postou")
	private String usuario;
	
	@Column(name = "problema_id")
	private Long problemaId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "arquivo_id",referencedColumnName="nome_arquivo")
	private Arquivo solucao;
	
	@JsonIgnore
	@ManyToOne(targetEntity=Problema.class, fetch=FetchType.EAGER)
    @JoinColumn(name="problema_id", referencedColumnName="problema_id", nullable=false, insertable = false, updatable = false)
	private Problema problema;
	
	public Solucao() {
		
	}

	public Solucao(Long id, String usuario, Long problemaId, Arquivo solucao, Problema problema) {
		this.id = id;
		this.usuario = usuario;
		this.problemaId = problemaId;
		this.solucao = solucao;
		this.problema = problema;
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

	public Long getProblemaId() {
		return problemaId;
	}

	public void setProblemaId(Long problemaId) {
		this.problemaId = problemaId;
	}

	public Problema getProblema() {
		return problema;
	}

	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	public Arquivo getSolucao() {
		return solucao;
	}

	public void setSolucao(Arquivo solucao) {
		this.solucao = solucao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, problema, problemaId, solucao, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solucao other = (Solucao) obj;
		return Objects.equals(id, other.id) && Objects.equals(problema, other.problema)
				&& Objects.equals(problemaId, other.problemaId) && Objects.equals(solucao, other.solucao)
				&& Objects.equals(usuario, other.usuario);
	}
}
