package com.rfhamster.maratonaDB.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rfhamster.maratonaDB.model.Report;

public interface ReportRepository extends JpaRepository<Report,Long>{
	@Query("SELECT r FROM Report r WHERE r.resolvido = true")
    List<Report> buscarReportsResolvidos();
	
	@Query("SELECT r FROM Report r WHERE r.resolvido = false")
    List<Report> buscarReportsNaoResolvidos();
	
	@Query("SELECT r FROM Report r WHERE r.usuario = :usuario")
    List<Report> buscarPorUsuario(@Param("usuario") String usuario);
}
