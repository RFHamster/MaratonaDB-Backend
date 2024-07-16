package com.rfhamster.maratonaDB.vo;

import java.util.Objects;

import com.rfhamster.maratonaDB.enums.TipoENUM;

public class ReportVO {
	
	private String usuario;
	private Long id_origem;
	private TipoENUM origem;
	private String mensagem;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(id_origem, mensagem, origem, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportVO other = (ReportVO) obj;
		return Objects.equals(id_origem, other.id_origem) && Objects.equals(mensagem, other.mensagem)
				&& origem == other.origem && Objects.equals(usuario, other.usuario);
	}
	
	
}	
