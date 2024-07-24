package com.rfhamster.maratonaDB.vo;

import java.util.Objects;

public class DicaInVO {
	private Long problemaId;
	private String usuario;
	private String conteudo;
	public Long getProblemaId() {
		return problemaId;
	}
	public void setProblemaId(Long problemaId) {
		this.problemaId = problemaId;
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
	@Override
	public int hashCode() {
		return Objects.hash(conteudo, problemaId, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DicaInVO other = (DicaInVO) obj;
		return Objects.equals(conteudo, other.conteudo) && Objects.equals(problemaId, other.problemaId)
				&& Objects.equals(usuario, other.usuario);
	}

}
