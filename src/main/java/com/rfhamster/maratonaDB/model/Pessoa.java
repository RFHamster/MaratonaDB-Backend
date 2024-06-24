package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="pessoas")
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_id")
	private Long id;
	
	@Column(name = "nome")
	private String nomeCompleto;
	
	@Column(name = "matricula_UFU", unique = true)
	private String matricula;
	
	@Column(name = "cpf", unique = true)
	private String cpf;
	
	@Column(name = "rg", unique = true)
	private String rg;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tamanho_camisa")
	private TamanhoCamisaENUM tamanhoCamisa;
	
	@Column(name = "babylook")
	private Boolean babylook;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "primeiraGraduacao")
	private Boolean primeiraGrad;
	
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, mappedBy = "pessoa")
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private User usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public TamanhoCamisaENUM getTamanhoCamisa() {
		return tamanhoCamisa;
	}
	public void setTamanhoCamisa(TamanhoCamisaENUM tamanhoCamisa) {
		this.tamanhoCamisa = tamanhoCamisa;
	}
	public Boolean getBabylook() {
		return babylook;
	}
	public void setBabylook(Boolean babylook) {
		this.babylook = babylook;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Boolean getPrimeiraGrad() {
		return primeiraGrad;
	}
	public void setPrimeiraGrad(Boolean primeiraGrad) {
		this.primeiraGrad = primeiraGrad;
	}
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	@Override
	public int hashCode() {
		return Objects.hash(babylook, cpf, email, id, matricula, nomeCompleto, primeiraGrad, rg, tamanhoCamisa,
				telefone, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(babylook, other.babylook) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(matricula, other.matricula) && Objects.equals(nomeCompleto, other.nomeCompleto)
				&& Objects.equals(primeiraGrad, other.primeiraGrad) && Objects.equals(rg, other.rg)
				&& tamanhoCamisa == other.tamanhoCamisa && Objects.equals(telefone, other.telefone)
				&& Objects.equals(usuario, other.usuario);
	}
}
