package com.rfhamster.maratonaDB.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rfhamster.maratonaDB.config.FileStorageConfig;
import com.rfhamster.maratonaDB.exceptions.FileStorageException;
import com.rfhamster.maratonaDB.exceptions.MyFileNotFoundException;
import com.rfhamster.maratonaDB.model.Arquivo;
import com.rfhamster.maratonaDB.repositories.ArquivoRepository;

@Service
public class ArquivoService {
	
	@Autowired
	ArquivoRepository repository;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public ArquivoService(FileStorageConfig fileStorageConfig) {
		
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		}catch (Exception e){
			throw new FileStorageException("Nao conseguiu criar diretorio de arquivos de upload",e);
		}
	}
	
	public Arquivo storeFile(MultipartFile file) {
		String originalName = StringUtils.cleanPath(file.getOriginalFilename());
		String uniqueName = UUID.randomUUID().toString() + "_" + originalName;
		
		try {
			if(originalName.contains("..")) {
				throw new FileStorageException("Nome do arquivo: " + originalName + " invalido!");
			}
			
			Path targetLocation = this.fileStorageLocation.resolve(uniqueName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new FileStorageException("Nao conseguiu armazenar o arquivo " + originalName,e);
		}
		String endpointUrl = "/donloadFile/" + uniqueName;
		return new Arquivo(uniqueName, endpointUrl, file.getContentType(), file.getSize());
	}
	
	public Arquivo salvar(Arquivo a) {
		return repository.save(a);
	}

	public Arquivo buscarById(Long id) {
		Optional<Arquivo> arquivo = repository.findById(id);
        return arquivo.orElse(null);
	}
	
	public Arquivo buscarByFilename(String filename) {
		Optional<Arquivo> arquivo = repository.findByFilename(filename);
        return arquivo.orElse(null);
	}
	
	public Resource loadFileAsResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}else {
				throw new MyFileNotFoundException("Arquivo nao achado");
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("Arquivo nao achado: " + filename,e);
		}
	}
	
	public Resource loadFileAsResourceById(Long id) {
		Arquivo arquivo = buscarById(id);
		if(arquivo == null) {
			return null;
		}
		return loadFileAsResource(arquivo.getFileName());
	}
	
	public void deleteFile(String filename) {
	    try {
	        Path filePath = this.fileStorageLocation.resolve(filename).normalize();
	        File file = filePath.toFile();
	        if (file.exists()) {
	            if (!file.delete()) {
	            	throw new FileStorageException("Falha ao excluir o arquivo: " + filename);
	            }
	        } else {
	            throw new MyFileNotFoundException("Arquivo não achado");
	        }
	    } catch (Exception e) {
	        throw new MyFileNotFoundException("Arquivo não achado: " + filename, e);
	    }
	}
	
	public boolean deletar(Long id) {
		Arquivo a = buscarById(id);
		if(a == null) {
			return false;
		}
		deleteFile(a.getFileName());
		repository.delete(a);
		return true;
	}
}
