package com.rfhamster.maratonaDB.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rfhamster.maratonaDB.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo,String>{
	@Query("SELECT a FROM Arquivo a WHERE a.fileName =:fileName")
	Optional<Arquivo> findByFilename(@Param("fileName") String fileName);
}
