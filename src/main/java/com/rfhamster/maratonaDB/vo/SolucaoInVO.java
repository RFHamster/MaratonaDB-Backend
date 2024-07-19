package com.rfhamster.maratonaDB.vo;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

public class SolucaoInVO {
	private Long problemaId;
	private String usuario;
	MultipartFile file;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public int hashCode() {
		return Objects.hash(file, problemaId, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolucaoInVO other = (SolucaoInVO) obj;
		return Objects.equals(file, other.file) && Objects.equals(problemaId, other.problemaId)
				&& Objects.equals(usuario, other.usuario);
	}
}
