package com.rfhamster.maratonaDB.vo.security;

import java.time.LocalDate;
import java.util.Objects;

import com.rfhamster.maratonaDB.enums.TamanhoCamisaENUM;

public class AccountSignInVO {
	private String username;
	private String password;
	private String nomeCompleto;
	private String matricula;
	private String cpf;
	private String rg;
	private String orgaoEmissor;
	private TamanhoCamisaENUM tamanhoCamisa;
	private String email;
	private String telefone;
	private Boolean primeiraGrad;
	private LocalDate dataEntrada;
	
	public AccountSignInVO() {
	}

	public AccountSignInVO(String username, String password, String nomeCompleto, String matricula, String cpf,
			String rg, String orgaoEmissor, TamanhoCamisaENUM tamanhoCamisa, String email, String telefone,
			Boolean primeiraGrad, LocalDate dataEntrada) {
		this.username = username;
		this.password = password;
		this.nomeCompleto = nomeCompleto;
		this.matricula = matricula;
		this.cpf = cpf;
		this.rg = rg;
		this.orgaoEmissor = orgaoEmissor;
		this.tamanhoCamisa = tamanhoCamisa;
		this.email = email;
		this.telefone = telefone;
		this.primeiraGrad = primeiraGrad;
		this.dataEntrada = dataEntrada;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public TamanhoCamisaENUM getTamanhoCamisa() {
		return tamanhoCamisa;
	}

	public void setTamanhoCamisa(TamanhoCamisaENUM tamanhoCamisa) {
		this.tamanhoCamisa = tamanhoCamisa;
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

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, dataEntrada, email, matricula, nomeCompleto, orgaoEmissor,
				password, primeiraGrad, rg, tamanhoCamisa, telefone, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountSignInVO other = (AccountSignInVO) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(dataEntrada, other.dataEntrada)
				&& Objects.equals(email, other.email) && Objects.equals(matricula, other.matricula)
				&& Objects.equals(nomeCompleto, other.nomeCompleto) && Objects.equals(orgaoEmissor, other.orgaoEmissor)
				&& Objects.equals(password, other.password) && Objects.equals(primeiraGrad, other.primeiraGrad)
				&& Objects.equals(rg, other.rg) && tamanhoCamisa == other.tamanhoCamisa
				&& Objects.equals(telefone, other.telefone) && Objects.equals(username, other.username);
	}
}
