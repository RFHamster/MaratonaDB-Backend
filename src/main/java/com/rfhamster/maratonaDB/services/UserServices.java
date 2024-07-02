package com.rfhamster.maratonaDB.services;

import java.util.Optional;

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
	
	public User buscarPorId(Long id) {
        Optional<User> usuario = repository.findById(id);
        return usuario.orElse(null);
    }
	
	public User atualizarFaixa(Long id, FaixasEnum faixa) {
		Optional<User> userExistente = repository.findById(id);
        if (userExistente.isPresent()) {
        	User u = userExistente.get();
        	u.setFaixa(faixa);
            return repository.save(u);
        } else {
            return null;
        }
	}
	
	public User atualizarPontos(Long id, Long valor, Boolean adicionar) {
		Optional<User> userExistente = repository.findById(id);
        if (userExistente.isPresent()) {
        	User u = userExistente.get();
        	if(adicionar) {
        		u.setPontos(u.getPontos() + valor);
        	}else {
        		u.setPontos(u.getPontos() - valor);
        	}
            return repository.save(u);
        } else {
            return null;
        }
	}
	
	public User atualizarUser(Long id, String username, String pass) {
		Optional<User> userExistente = repository.findById(id);
        if (userExistente.isPresent()) {
        	User u = userExistente.get();
        	u.setUsername(username);
        	u.setPassword(pass);
            return repository.save(u);
        } else {
            return null;
        }
	}
	
	public boolean deletar(Long id) {
		pessoaRepository.deleteById(id);
        repository.deleteById(id);
		return true;
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
