package com.rfhamster.maratonaDB.vo;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.rfhamster.maratonaDB.enums.TipoENUM;

public class ReportVO extends RepresentationModel<SolucaoVO>{
	private Long keyReport;
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
	public Long getKeyReport() {
		return keyReport;
	}
	public void setKeyReport(Long keyReport) {
		this.keyReport = keyReport;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id_origem, keyReport, mensagem, origem, usuario);
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
		ReportVO other = (ReportVO) obj;
		return Objects.equals(id_origem, other.id_origem) && Objects.equals(keyReport, other.keyReport)
				&& Objects.equals(mensagem, other.mensagem) && origem == other.origem
				&& Objects.equals(usuario, other.usuario);
	}
}	
