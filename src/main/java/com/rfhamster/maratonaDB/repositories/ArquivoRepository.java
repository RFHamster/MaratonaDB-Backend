package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfhamster.maratonaDB.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo,String>{

}
