package com.rfhamster.maratonaDB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.model.Problema;

public interface ProblemaRepository extends JpaRepository<Problema,Long>{
	@Query("SELECT p FROM Problema p WHERE p.ativo = true")
    List<Problema> buscarProblemasAtivos();
	
	@Query("SELECT p FROM Problema p WHERE p.ativo = false")
    List<Problema> buscarProblemasDesativados();
	
	@Query("SELECT p FROM Problema p WHERE p.faixa = :faixa")
    List<Problema> buscarPorFaixa(@Param("faixa") FaixasEnum faixa);
	
	@Query("SELECT p FROM Problema p WHERE p.origem = :origem")
    List<Problema> buscarPorOrigem(@Param("origem") String origem);
	
	@Query("SELECT p FROM Problema p WHERE p.assuntos LIKE %:termo%")
	List<Problema> buscarPorAssunto(@Param("termo") String termo);
	
	@Query("SELECT p FROM Problema p WHERE p.titulo LIKE %:termo%")
	List<Problema> buscarPorTitulo(@Param("termo") String termo);
	
	@Query("SELECT p FROM Problema p WHERE p.idOriginal = :idOriginal")
	List<Problema> buscarIdOrigem(@Param("idOriginal") String idOriginal);
}
