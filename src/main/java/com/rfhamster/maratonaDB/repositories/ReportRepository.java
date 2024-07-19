package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rfhamster.maratonaDB.enums.TipoENUM;
import com.rfhamster.maratonaDB.model.Report;

public interface ReportRepository extends JpaRepository<Report,Long>{
	@Query("SELECT r FROM Report r WHERE r.resolvido = true")
	Page<Report> buscarReportsResolvidos(Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.resolvido = false")
	Page<Report> buscarReportsNaoResolvidos(Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.usuario = :usuario")
	Page<Report> buscarPorUsuario(@Param("usuario") String usuario, Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.origem = PROBLEMA")
	Page<Report> buscarReportsProblemas(Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.origem = DICA")
	Page<Report> buscarReportsDicas(Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.origem = SOLUCAO")
	Page<Report> buscarReportsSolucoes(Pageable pageable);
	
	@Query("SELECT r FROM Report r WHERE r.origem = :origem AND r.id_origem = :id_origem")
	Page<Report> buscarReportsPorOrigemEId(@Param("origem") TipoENUM origem, @Param("id_origem") Long id_origem, Pageable pageable);
	
}
