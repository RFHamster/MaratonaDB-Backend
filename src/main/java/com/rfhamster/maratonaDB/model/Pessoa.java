package com.rfhamster.maratonaDB.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rfhamster.maratonaDB.enums.TamanhoCamisaENUM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="pessoas")
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
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
	
	@Column(name = "orgao_emissor_rg", unique = false)
	private String orgaoEmissor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tamanho_camisa")
	private TamanhoCamisaENUM tamanhoCamisa;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "primeiraGraduacao")
	private Boolean primeiraGrad;
	
	@Column(name = "atribuicoes")
	private String atribuicao;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "entrada_projeto")
	private LocalDate dataEntrada;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "saida_projeto")
	private LocalDate dataSaida;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "pessoa_id")
	private User usuario;
	
	public Pessoa() {}
	
	public Pessoa(String nomeCompleto, String matricula, String cpf, String rg, String orgaoEmissor,
			TamanhoCamisaENUM tamanhoCamisa, String email, String telefone, Boolean primeiraGrad,
			LocalDate dataEntrada, User usuario) {
		this.nomeCompleto = nomeCompleto;
		this.matricula = matricula;
		this.cpf = cpf;
		this.rg = rg;
		this.orgaoEmissor = orgaoEmissor;
		this.tamanhoCamisa = tamanhoCamisa;
		this.email = email;
		this.telefone = telefone;
		this.primeiraGrad = primeiraGrad;
		this.atribuicao = "";
		this.dataEntrada = dataEntrada;
		this.dataSaida = null;
		this.usuario = usuario;
	}
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
	public String getAtribuicao() {
		return atribuicao;
	}
	public void setAtribuicao(String atribuicao) {
		this.atribuicao = atribuicao;
	}
	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}
	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}
	public LocalDate getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public LocalDate getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}
	@Override
	public int hashCode() {
		return Objects.hash(atribuicao, cpf, dataEntrada, dataSaida, email, id, matricula, nomeCompleto, orgaoEmissor,
				primeiraGrad, rg, tamanhoCamisa, telefone, usuario);
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
		return Objects.equals(atribuicao, other.atribuicao) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(dataEntrada, other.dataEntrada) && Objects.equals(dataSaida, other.dataSaida)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(matricula, other.matricula) && Objects.equals(nomeCompleto, other.nomeCompleto)
				&& Objects.equals(orgaoEmissor, other.orgaoEmissor) && Objects.equals(primeiraGrad, other.primeiraGrad)
				&& Objects.equals(rg, other.rg) && tamanhoCamisa == other.tamanhoCamisa
				&& Objects.equals(telefone, other.telefone) && Objects.equals(usuario, other.usuario);
	}
}
