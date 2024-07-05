package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="arquivos")
public class Arquivo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "arquivo_id")
	private Long id;
	
	@Column(name = "nome_arquivo", unique=true)
	private String fileName;
	
	@Column(name = "url_download")
	private String downloadUlr;
	
	@Column(name = "tipo_arquivo")
	private String fileType;
	
	@Column(name = "tamanho")
	private Long size;
	
	public Arquivo() {}
	
	public Arquivo(String fileName, String downloadUlr, String fileType, Long size) {
		super();
		this.fileName = fileName;
		this.downloadUlr = downloadUlr;
		this.fileType = fileType;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		return Objects.hash(downloadUlr, fileName, fileType, id, size);
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
				&& Objects.equals(fileType, other.fileType) && Objects.equals(id, other.id)
				&& Objects.equals(size, other.size);
	}
}
