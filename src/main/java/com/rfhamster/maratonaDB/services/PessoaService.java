package com.rfhamster.maratonaDB.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.repositories.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository repository;
	
    public Pessoa buscar(Long id) {
        Optional<Pessoa> pessoa = repository.findById(id);
        return pessoa.orElse(null);
    }

    public Pessoa atualizar(Long id, Pessoa novaPessoa) {
        Optional<Pessoa> pessoaExistente = repository.findById(id);
        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();
            pessoa.setNomeCompleto(novaPessoa.getNomeCompleto());
            pessoa.setMatricula(novaPessoa.getMatricula());
            pessoa.setCpf(novaPessoa.getCpf());
            pessoa.setRg(novaPessoa.getRg());
            pessoa.setOrgaoEmissor(novaPessoa.getOrgaoEmissor());
            pessoa.setTamanhoCamisa(novaPessoa.getTamanhoCamisa());
            pessoa.setEmail(novaPessoa.getEmail());
            pessoa.setTelefone(novaPessoa.getTelefone());
            pessoa.setPrimeiraGrad(novaPessoa.getPrimeiraGrad());
            pessoa.setAtribuicao(novaPessoa.getAtribuicao());
            pessoa.setDataEntrada(novaPessoa.getDataEntrada());
            pessoa.setDataSaida(novaPessoa.getDataSaida());
            
            try {
            	return repository.save(pessoa);
    		} catch (DataIntegrityViolationException  e) {
    			System.out.println(e.getMessage());
                throw e;
    		}	
        } else {
            return null;
        }
    }
    
    public Pessoa atualizarAtribuicao(Long id, String atribuicao) {
        Optional<Pessoa> pessoaExistente = repository.findById(id);
        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();
            pessoa.setAtribuicao(atribuicao);
            return repository.save(pessoa);
        } else {
            return null;
        }
    }

    public boolean deletar(Long id) {
        repository.deleteById(id);
		return true;
    }
}
