package com.rfhamster.maratonaDB.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.model.User;
import com.rfhamster.maratonaDB.repositories.UserRepository;
import com.rfhamster.maratonaDB.securityJwt.JwtTokenProvider;
import com.rfhamster.maratonaDB.services.UserServices;
import com.rfhamster.maratonaDB.vo.UserSigninVO;
import com.rfhamster.maratonaDB.vo.security.AccountCredentialsVO;
import com.rfhamster.maratonaDB.vo.security.AccountSignInVO;
import com.rfhamster.maratonaDB.vo.security.TokenVO;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserServices service;
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> registrarUserComum(@RequestBody AccountSignInVO data) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Pessoa p = new Pessoa(data.getNomeCompleto(), data.getMatricula(), data.getCpf(), data.getRg(), data.getOrgaoEmissor(),
				data.getTamanhoCamisa(), data.getEmail(), data.getTelefone(), data.getPrimeiraGrad(),
				data.getDataEntrada(), null);
		User u = service.salvarUserComum(data.getUsername(), encoder.encode(data.getPassword()), p);
		return ResponseEntity.ok(u);
	}

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		Map<String, Object> dadosAutenticacao = new HashMap<String, Object>();
		UserSigninVO userVo;
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if(!authenticate.isAuthenticated()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
			}
			var user = repository.findByUsername(username);
			
			if (user != null) {
				Pessoa p = user.getPessoa();
				p.setUsuario(null);
				userVo = new UserSigninVO(user.getId(), username, user.getRole(), user.getFaixa(), user.getPontos(), p);
				dadosAutenticacao.put("tokenResponse", tokenProvider.createAccessToken(username, user.getRoles()));
				dadosAutenticacao.put("usuario", userVo);
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
		return ResponseEntity.ok(dadosAutenticacao);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username,
			@RequestHeader("Authorization") String refreshToken) {

		if (checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		
		var user = repository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		
		
		if (tokenResponse == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		return ResponseEntity.ok(tokenResponse);
	}

	
	
	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				 || data.getPassword() == null || data.getPassword().isBlank();
	}
}
