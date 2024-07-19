package com.rfhamster.maratonaDB.vo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.rfhamster.maratonaDB.controllers.ArquivoController;

public class ArquivoVO extends RepresentationModel<ArquivoVO>{
	private String fileName;
	private String fileType;
	private Long size;
	
	public ArquivoVO() {}
	

	public ArquivoVO(String fileName, String fileType, Long size) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		this.add(linkTo(methodOn(ArquivoController.class).buscar(fileName)).withSelfRel());
		this.add(linkTo(methodOn(ArquivoController.class).loadResource(fileName)).withRel("download"));
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fileName, fileType, size);
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
		ArquivoVO other = (ArquivoVO) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(fileType, other.fileType)
				&& Objects.equals(size, other.size);
	}
	
	
}
