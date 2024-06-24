package com.rfhamster.maratonaDB.model;

public enum FaixasEnum {
	BRANCA("branca"),
	AMARELA("AMARELA"),
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
