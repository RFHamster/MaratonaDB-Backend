package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfhamster.maratonaDB.model.Problema;

public interface ProblemaRepository extends JpaRepository<Problema,Long>{

}
