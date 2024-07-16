package com.rfhamster.maratonaDB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public User salvarUserComum (String username, String password, Pessoa p) throws DataIntegrityViolationException{
		if(p == null) {
			return null;
		}
		
		User usuario = new User(username, password, true, true, true, true, UserRole.USER, FaixasEnum.BRANCA,0L,null);
		p.setUsuario(usuario);
		try {
			usuario.setPessoa(pessoaRepository.save(p));
			return repository.save(usuario);
		} catch (DataIntegrityViolationException  e) {
			System.out.println(e.getMessage());
            throw e;
		}		
	}
	
	public User salvarUserADM(String username, String password) {
		Pessoa p = new Pessoa();
		User usuario = new User(username, password, true, true, true, true, UserRole.ADMIN, FaixasEnum.PRETA,0L,null);
		p.setUsuario(usuario);
		usuario.setPessoa(pessoaRepository.save(p));
		repository.save(usuario);
		return usuario;
	}
	
	public List<User> buscarTodos() {
        List<User> usuarios = repository.findAll();
        return usuarios;
    }
	
	public User buscar(Long id) {
        Optional<User> usuario = repository.findById(id);
        return usuario.orElse(null);
    }
	
	public User buscarCPF(String cpf) {
        Optional<User> usuario = repository.buscarPorCpfDaPessoa(cpf);
        return usuario.orElse(null);
    }
	
	public User buscarMatricula(String matricula) {
        Optional<User> usuario = repository.buscarPorMatriculaDaPessoa(matricula);
        return usuario.orElse(null);
    }
	
	public User buscarUsuario(String username) {
        Optional<User> usuario = repository.findByUsernameOP(username);
        return usuario.orElse(null);
    }
	
	public User buscarRG(String rg) {
        Optional<User> usuario = repository.buscarPorRgDaPessoa(rg);
        return usuario.orElse(null);
    }
	
	public List<User> buscarTermoNomeCompleto(String termo) {
        List<User> usuario = repository.buscarPorNomeCompletoParcial(termo);
        return usuario;
    }
	
	public User atualizarFaixa(Long id, FaixasEnum faixa) {
		Optional<User> userExistente = repository.findById(id);
		if(!userExistente.isPresent()) {
			return null;
		}
    	User u = userExistente.get();
    	u.setFaixa(faixa);
        return repository.save(u);
		
//		User userExistente;
//		try {
//			userExistente = buscar(id);
//			userExistente.setFaixa(faixa);
//	    	return repository.save(userExistente);
//		} catch (NoSuchElementException e) {
//			throw e;
//		}
	}
	
	public User atualizarPontos(Long id, Long valor, Boolean adicionar) {
		Optional<User> userExistente = repository.findById(id);
		if(!userExistente.isPresent()) {
			return null;
		}
    	User u = userExistente.get();
    	if(adicionar) {
    		u.setPontos(u.getPontos() + valor);
    	}else {
    		u.setPontos(u.getPontos() - valor);
    	}
        return repository.save(u);
	}
	
	public User atualizarPontos(String username, Long valor, Boolean adicionar) {
		Optional<User> userExistente = repository.findByUsernameOP(username);
		if(!userExistente.isPresent()) {
			return null;
		}
    	User u = userExistente.get();
    	if(adicionar) {
    		u.setPontos(u.getPontos() + valor);
    	}else {
    		u.setPontos(u.getPontos() - valor);
    	}
        return repository.save(u);
	}
	
	public User atualizarUser(Long id, String username, String pass) {
		Optional<User> userExistente = repository.findById(id);
		if(!userExistente.isPresent()) {
			return null;
		}
    	User u = userExistente.get();
    	u.setUsername(username);
    	u.setPassword(pass);
        return repository.save(u);
	}
	
	public User atualizarUser(String username, String pass) {
		User userExistente = buscarUsuario(username);
		if(userExistente == null) {
			return null;
		}
		userExistente.setPassword(pass);
        return repository.save(userExistente);
	}
	
	public boolean desabilitarUsuario(Long id) {
		Optional<User> userExistente = repository.findById(id);
		if(!userExistente.isPresent()) {
			return false;
		}
    	User u = userExistente.get();
    	u.setPontos(0L);
    	u.setEnabled(false);
    	repository.save(u);
        return true;
	}
	
	public boolean habilitarUsuario(Long id) {
		Optional<User> userExistente = repository.findById(id);
		if(!userExistente.isPresent()) {
			return false;
		}
    	User u = userExistente.get();
    	u.setPontos(0L);
    	u.setEnabled(true);
    	repository.save(u);
        return true;
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
