package com.rfhamster.maratonaDB.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	@GetMapping(value = "")
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
			User u = userService.buscar(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/cpf/{codigo}")
	public ResponseEntity< ? > buscarCPF(@PathVariable String codigo) {
		try {
			User u = userService.buscarCPF(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/rg/{codigo}")
	public ResponseEntity< ? > buscarRG(@PathVariable String codigo) {
		try {
			User u = userService.buscarRG(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/username/{codigo}")
	public ResponseEntity< ? > buscarUsername(@PathVariable String codigo) {
		try {
			User u = userService.buscarUsuario(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/nomecompleto/termo/{codigo}")
	public ResponseEntity< ? > buscarTermoNome(@PathVariable String codigo) {
		try {
			List<User> u = userService.buscarTermoNomeCompleto(codigo);
			if(u.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuario encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@GetMapping(path = "/matricula/{codigo}")
	public ResponseEntity< ? > buscarMatricula(@PathVariable String codigo) {
		try {
			User u = userService.buscarMatricula(codigo);
			if(u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}		
	}
	
	@PutMapping(path = "/{codigo}")
	public ResponseEntity<?> atualizar(@PathVariable Long codigo, @RequestBody AccountSignInVO data) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Pessoa p = new Pessoa(data.getNomeCompleto().toUpperCase(),data.getMatricula(),data.getCpf(),data.getRg(),data.getOrgaoEmissor(),
        		data.getTamanhoCamisa(), data.getEmail(),data.getTelefone(),data.getPrimeiraGrad(),data.getDataEntrada(), null);
		try {
			User usuarioAtualizado = userService.atualizarUser(codigo, data.getUsername(), encoder.encode(data.getPassword()));
	        Pessoa pessoaAtualizada = pessoaService.atualizar(codigo, p);
	        
	        
	        usuarioAtualizado.setPessoa(pessoaAtualizada);
	        return ResponseEntity.ok(usuarioAtualizado);
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
	
	@PutMapping(path = "/atribuicao/{codigo}")
	public ResponseEntity<?> atualizarAtribuicoes(@PathVariable Long codigo, @RequestBody String atribuicao) {
	    try {
	    	Pessoa p = pessoaService.atualizarAtribuicao(codigo, atribuicao);
	        if(p == null) {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
	        }
	        return ResponseEntity.ok(p);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@PutMapping(path = "/desabilitar/{codigo}")
	public ResponseEntity<?> desabilitar(@PathVariable Long codigo) {
	    try {
	        userService.desabilitarUsuario(codigo);
	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@PutMapping(path = "/habilitar/{codigo}")
	public ResponseEntity<?> habilitar(@PathVariable Long codigo) {
	    try {
	        userService.habilitarUsuario(codigo);
	        return ResponseEntity.noContent().build();
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
