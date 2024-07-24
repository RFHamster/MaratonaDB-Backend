package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.Objects;

import com.rfhamster.maratonaDB.enums.TipoENUM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "solucao_id")
	private Long id;
	
	@Column(name = "usuario_postou")
	private String usuario;
	
	@Column(name = "id_origem")
	private Long id_origem;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_origem")
	private TipoENUM origem;
	
	@Column(name = "mensagem")
	private String mensagem;
	
	@Column(name = "status_resolvido")
	private Boolean resolvido;
	
	public Report() {
		
	}
	
	public Report(Long id, String usuario, Long id_origem, TipoENUM origem, String mensagem, Boolean resolvido) {
		this.id = id;
		this.usuario = usuario;
		this.id_origem = id_origem;
		this.origem = origem;
		this.mensagem = mensagem;
		this.resolvido = resolvido;
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

	public Long getId_origem() {
		return id_origem;
	}

	public void setId_origem(Long id_origem) {
		this.id_origem = id_origem;
	}

	public TipoENUM getOrigem() {
		return origem;
	}

	public void setOrigem(TipoENUM origem) {
		this.origem = origem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Boolean getResolvido() {
		return resolvido;
	}

	public void setResolvido(Boolean resolvido) {
		this.resolvido = resolvido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, id_origem, mensagem, origem, resolvido, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		return Objects.equals(id, other.id) && Objects.equals(id_origem, other.id_origem)
				&& Objects.equals(mensagem, other.mensagem) && origem == other.origem
				&& Objects.equals(resolvido, other.resolvido) && Objects.equals(usuario, other.usuario);
	}
}
