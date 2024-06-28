package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "problema_id")
	private Arquivo problema;
	
	@OneToMany(mappedBy="problema")
	private List<Dicas> dicas;
	
	@OneToMany(mappedBy="problema")
	private List<Solucao> solucoes;

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

	@Override
	public int hashCode() {
		return Objects.hash(assuntos, dicas, id, idOriginal, origem, solucoes, titulo, usuario);
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
		return Objects.equals(assuntos, other.assuntos) && Objects.equals(dicas, other.dicas)
				&& Objects.equals(id, other.id) && Objects.equals(idOriginal, other.idOriginal)
				&& Objects.equals(origem, other.origem) && Objects.equals(solucoes, other.solucoes)
				&& Objects.equals(titulo, other.titulo) && Objects.equals(usuario, other.usuario);
	}
	
	
}
