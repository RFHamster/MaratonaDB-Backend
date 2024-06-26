package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="arquivos")
public class Arquivo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nome_arquivo")
	private String fileName;
	
	@Column(name = "url_download")
	private String downloadUlr;
	
	@Column(name = "tipo_arquivo")
	private String fileType;
	
	@Column(name = "tamanho")
	private Long size;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDownloadUlr() {
		return downloadUlr;
	}

	public void setDownloadUlr(String downloadUlr) {
		this.downloadUlr = downloadUlr;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		return Objects.hash(downloadUlr, fileName, fileType, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arquivo other = (Arquivo) obj;
		return Objects.equals(downloadUlr, other.downloadUlr) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(fileType, other.fileType) && Objects.equals(size, other.size);
	}
}
