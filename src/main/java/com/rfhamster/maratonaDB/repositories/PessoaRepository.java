package com.rfhamster.maratonaDB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfhamster.maratonaDB.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa,Long>{

}
