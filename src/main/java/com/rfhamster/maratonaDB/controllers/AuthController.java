package com.rfhamster.maratonaDB.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.rfhamster.maratonaDB.securityJwt.JwtTokenProvider;
import com.rfhamster.maratonaDB.services.UserServices;
import com.rfhamster.maratonaDB.vo.UserSigninVO;
import com.rfhamster.maratonaDB.vo.security.AccountCredentialsVO;
import com.rfhamster.maratonaDB.vo.security.AccountSignInVO;
import com.rfhamster.maratonaDB.vo.security.TokenVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth/v1")
@Tag(name="Autenticação", description = "Enpoint para autenticar/cadastrar usuários")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserServices service;
	
	@PostMapping(value = "/register")
	@Operation(summary = "Registrar um usuário comum", description = "Registrar um usuário comum com os dados fornecidos",
		    tags = {"Autenticação, Usuários"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = User.class))
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Requisição de cliente inválida",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "409",
		            description = "Conflito - Dados duplicados",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    }
		)
	public ResponseEntity<?> registrarUserComum(@RequestBody AccountSignInVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Pessoa p = new Pessoa(data.getNomeCompleto(), data.getMatricula(), data.getCpf(), data.getRg(), data.getOrgaoEmissor(),
				data.getTamanhoCamisa(), data.getEmail(), data.getTelefone(), data.getPrimeiraGrad(),
				data.getDataEntrada(), null);
		User u;
		try {
			u = service.salvarUserComum(data.getUsername(), encoder.encode(data.getPassword()), p);
			return ResponseEntity.ok(u);
		} catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("for key 'users.UKr43af9ap4edm43mmtq01oddj6'")) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
                }
                if (e.getMessage().contains("for key 'pessoas.UKc7pqbmo6e96slvonilywsb8oe'")) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF already exists");
                }
                if (e.getMessage().contains("for key 'pessoas.UKhfwva4ba1blfdj9rrab2emdqw'")) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Matricula already exists");
                }
                if (e.getMessage().contains("for key 'pessoas.UKggc1gmaya889fgcl2u3t2s1cw'")) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("RG already exists");
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
		
	}
	
	@PostMapping(value = "/register/adm")
	@Operation(summary = "Registrar um administrador", description = "Registrar um usuário administrador com os dados fornecidos", tags = {"Usuário"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = User.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    }
		)
	public ResponseEntity<?> registrarUserADM(@RequestBody AccountCredentialsVO data) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		User u = service.salvarUserADM(data.getUsername(), encoder.encode(data.getPassword()));
		return ResponseEntity.ok(u);
	}
	
	@PutMapping(value = "/atualizar-usuario")
	@Operation(summary = "Atualizar usuário",description = "Atualizar as informações de um usuário",tags = {"Usuário"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = User.class))
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Usuário não encontrado",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    }
		)
	public ResponseEntity<?> atualizarUsuario(@RequestBody AccountCredentialsVO data) {
		try {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			User u = service.atualizarUser(data.getUsername(), encoder.encode(data.getPassword()));
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}	
	}

	@PostMapping(value = "/signin")
	@Operation(summary = "Autenticação de usuário",description = "Autenticar um usuário com as credenciais fornecidas",tags = {"Autenticação"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = Map.class))
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Requisição de cliente inválida",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    }
		)
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		Map<String, Object> dadosAutenticacao = new HashMap<String, Object>();
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if(!authenticate.isAuthenticated()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
			}
			
			UserSigninVO userVo = service.buscarUsuarioRetornoVO(username);
			if(userVo == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username/password supplied!");
			}
			
			dadosAutenticacao.put("tokenResponse", tokenProvider.createAccessToken(username, userVo.getRoles()));
			dadosAutenticacao.put("usuario", userVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
		return ResponseEntity.ok(dadosAutenticacao);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/refresh/{username}")
	@Operation(summary = "Atualizar token",description = "Atualizar o token de um usuário",tags = {"Autenticação"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Sucesso",
		            content = @Content(schema = @Schema(implementation = TokenVO.class))
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Requisição de cliente inválida",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Usuário não encontrado",
		            content = @Content(schema = @Schema(implementation = String.class))
		        ),
		        @ApiResponse(
		            responseCode = "500",
		            description = "Erro interno do servidor",
		            content = @Content(schema = @Schema(implementation = String.class))
		        )
		    }
		)
	public ResponseEntity refreshToken(@PathVariable("username") String username,
			@RequestHeader("Authorization") String refreshToken) {

		if (checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		
		var user = service.buscarUsuario(username);
		
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
	
	private boolean checkIfParamsIsNotNull(AccountSignInVO data) {
		return data == null || data.getPassword().isBlank() || data.getUsername().isBlank()
				 || data.getNomeCompleto().isBlank() || data.getMatricula().isBlank()
				 || data.getCpf().isBlank() || data.getRg().isBlank()
				 || data.getOrgaoEmissor().isBlank() || data.getTamanhoCamisa().toString() == ""
				 || data.getEmail().isBlank() || data.getTelefone().isBlank()
				 || data.getPrimeiraGrad() == null || data.getDataEntrada() == null;
	}
}
