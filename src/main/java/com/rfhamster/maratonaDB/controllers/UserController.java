package com.rfhamster.maratonaDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.model.User;
import com.rfhamster.maratonaDB.services.PessoaService;
import com.rfhamster.maratonaDB.services.UserServices;
import com.rfhamster.maratonaDB.vo.security.AccountSignInVO;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserServices userService;
	
	@Autowired
	PessoaService pessoaService;
	
	//Paginacao
	@GetMapping(value = "/")
	public ResponseEntity< ? > buscarTodos() {
		try {
			return ResponseEntity.ok(userService.buscarTodos());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/{codigo}")
	public ResponseEntity< ? > buscar(@PathVariable Long codigo) {
		try {
			return ResponseEntity.ok(userService.buscarPorId(codigo));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@PutMapping(path = "/{codigo}")
	public ResponseEntity<?> atualizar(@PathVariable Long codigo, @RequestBody AccountSignInVO data) {
	    try {
	    	PasswordEncoder encoder = new BCryptPasswordEncoder();
	    	//Falta Confirmar todos os dados, se nao tem repetido
	        User usuarioAtualizado = userService.atualizarUser(codigo, data.getUsername(), encoder.encode(data.getPassword()));
	        Pessoa p = new Pessoa(data.getNomeCompleto(),data.getMatricula(),data.getCpf(),data.getRg(),data.getOrgaoEmissor(),
	        		data.getTamanhoCamisa(), data.getEmail(),data.getTelefone(),data.getPrimeiraGrad(),data.getDataEntrada(), null);
	        Pessoa pessoaAtualizada = pessoaService.atualizar(codigo, p);
	        usuarioAtualizado.setPessoa(pessoaAtualizada);
	        return ResponseEntity.ok(usuarioAtualizado);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@DeleteMapping(path = "/{codigo}")
	public ResponseEntity<?> deletar(@PathVariable Long codigo) {
	    try {
	        userService.deletar(codigo);
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
}
