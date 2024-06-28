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
import jakarta.persistence.Table;

@Entity
@Table(name = "dicas")
public class Dicas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dica_id")
	private Long id;
	
	@Column(name = "usuario_postou")
	private String usuario;
	
	@Column(name = "conteudo_dica")
	private String conteudo;
	
	@Column(name = "problema_id")
	private Long problemaId;
	
	@JsonIgnore
	@ManyToOne(targetEntity=Problema.class, fetch=FetchType.EAGER)
    @JoinColumn(name="problema_id", referencedColumnName="problema_id", nullable=false, insertable = false, updatable = false)
	private Problema problema;

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

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
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

	@Override
	public int hashCode() {
		return Objects.hash(conteudo, id, problema, problemaId, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dicas other = (Dicas) obj;
		return Objects.equals(conteudo, other.conteudo) && Objects.equals(id, other.id)
				&& Objects.equals(problema, other.problema) && Objects.equals(problemaId, other.problemaId)
				&& Objects.equals(usuario, other.usuario);
	}
}
