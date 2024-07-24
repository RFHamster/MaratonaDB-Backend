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
	
	@Column(name = "tipo_arquivo")
	private String fileType;
	
	@Column(name = "tamanho")
	private Long size;
	
	public Arquivo() {}
	
	public Arquivo(String fileName, String fileType, Long size) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.size = size;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
		return Objects.hash(fileName, fileType, size);
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
		return Objects.equals(fileName, other.fileName)
				&& Objects.equals(fileType, other.fileType) && Objects.equals(size, other.size);
	}
}
