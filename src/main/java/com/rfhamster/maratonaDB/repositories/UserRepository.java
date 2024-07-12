package com.rfhamster.maratonaDB.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rfhamster.maratonaDB.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	@Query("SELECT u FROM User u WHERE u.username =:username")
	User findByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username =:username")
	Optional<User> findByUsernameOP(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.pessoa.cpf = :cpf")
	Optional<User> buscarPorCpfDaPessoa(@Param("cpf") String cpf);
	
	@Query("SELECT u FROM User u WHERE u.pessoa.matricula = :matricula")
	Optional<User> buscarPorMatriculaDaPessoa(@Param("matricula") String matricula);
	
	@Query("SELECT u FROM User u WHERE u.pessoa.rg = :rg")
	Optional<User> buscarPorRgDaPessoa(@Param("rg") String rg);
	
	@Query("SELECT u FROM User u WHERE u.pessoa.nomeCompleto LIKE %:termo%")
    List<User> buscarPorNomeCompletoParcial(@Param("termo") String termo);
}
