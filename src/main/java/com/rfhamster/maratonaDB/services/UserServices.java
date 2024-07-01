package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.model.User;
import com.rfhamster.maratonaDB.repositories.PessoaRepository;
import com.rfhamster.maratonaDB.repositories.UserRepository;
import com.rfhamster.maratonaDB.vo.security.UserRole;

@Service
public class UserServices implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}
	
	public User salvarUserComum(String username, String password, Pessoa p) {
		User usuario = new User(username, password, true, true, true, true, UserRole.USER, FaixasEnum.BRANCA,0L,null);
		if(p != null) {
			p.setUsuario(usuario);
			usuario.setPessoa(pessoaRepository.save(p));
		}
		repository.save(usuario);
		return usuario;
	}
	
	public User salvarUserADM(String username, String password) {
		User usuario = new User(username, password, true, true, true, true, UserRole.ADMIN, FaixasEnum.PRETA,0L,null);
		repository.save(usuario);
		return usuario;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
	}
}
