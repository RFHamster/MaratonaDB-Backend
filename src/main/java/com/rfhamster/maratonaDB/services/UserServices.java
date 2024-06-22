package com.rfhamster.maratonaDB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.User;
import com.rfhamster.maratonaDB.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}
	
	public User salvar(String userName, String password) {
		User usuario = new User();
		usuario.setUserName(userName);
		usuario.setPassword(password);
		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setCredentialsNonExpired(true);
		usuario.setEnabled(true);
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
