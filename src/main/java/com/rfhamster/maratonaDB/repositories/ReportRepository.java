package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfhamster.maratonaDB.model.Report;

public interface ReportRepository extends JpaRepository<Report,Long>{

}
