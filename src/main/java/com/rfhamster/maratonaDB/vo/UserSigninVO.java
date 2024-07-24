package com.rfhamster.maratonaDB.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rfhamster.maratonaDB.enums.FaixasEnum;
import com.rfhamster.maratonaDB.model.Pessoa;
import com.rfhamster.maratonaDB.vo.security.UserRole;

public class UserSigninVO extends RepresentationModel<UserSigninVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long key;
	private String username;
	private UserRole role;
	private FaixasEnum faixa;
	private Pessoa pessoa;
	private Long pontos;
	
	public UserSigninVO() {
		
	}
	
	public UserSigninVO(Long key, String username, UserRole role, FaixasEnum faixa, Long pontos, Pessoa pessoa) {
		super();
		this.key = key;
		this.username = username;
		this.role = role;
		this.faixa = faixa;
		this.pessoa = pessoa;
		this.pontos = pontos;
	}
	
	@JsonIgnore
	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		if(this.role == UserRole.ADMIN) roles.add("ROLE_ADMIN");
		return roles;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public FaixasEnum getFaixa() {
		return faixa;
	}

	public void setFaixa(FaixasEnum faixa) {
		this.faixa = faixa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getPontos() {
		return pontos;
	}

	public void setPontos(Long pontos) {
		this.pontos = pontos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(faixa, key, pessoa, pontos, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSigninVO other = (UserSigninVO) obj;
		return faixa == other.faixa && Objects.equals(key, other.key) && Objects.equals(pessoa, other.pessoa)
				&& Objects.equals(pontos, other.pontos) && role == other.role
				&& Objects.equals(username, other.username);
	}
	
	
}
