package com.rfhamster.maratonaDB.enums;

public enum FaixasEnum {
	SEM("sem_faixa"),
	BRANCA("branca"),
	AMARELA("amarela"),
	LARANJA("laranja"),
	VERDE("verde"),
	ROXO("roxo"),
	VERMELHO("vermelho"),
	MARROM("marrom"),
	PRETA("preta");
	
	private String faixa;
	
	FaixasEnum(String faixa) {
		this.faixa = faixa;
	}
	
	public String getFaixa() {
		return faixa;
	}
}
