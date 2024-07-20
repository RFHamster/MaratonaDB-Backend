package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.model.Problema;

public interface ProblemaRepository extends JpaRepository<Problema,Long>{
	@Query("SELECT p FROM Problema p WHERE p.ativo = true")
	Page<Problema> buscarProblemasAtivos(Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.ativo = false")
	Page<Problema> buscarProblemasDesativados(Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.faixa = :faixa")
	Page<Problema> buscarPorFaixa(@Param("faixa") FaixasEnum faixa, Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.origem = :origem")
	Page<Problema> buscarPorOrigem(@Param("origem") String origem, Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.assuntos LIKE %:termo%")
	Page<Problema> buscarPorAssunto(@Param("termo") String termo, Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.titulo LIKE %:termo%")
	Page<Problema> buscarPorTitulo(@Param("termo") String termo, Pageable pageable);
	
	@Query("SELECT p FROM Problema p WHERE p.idOriginal = :idOriginal")
	Page<Problema> buscarIdOrigem(@Param("idOriginal") String idOriginal, Pageable pageable);
}
