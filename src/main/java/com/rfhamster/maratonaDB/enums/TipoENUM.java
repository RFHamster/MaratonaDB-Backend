package com.rfhamster.maratonaDB.enums;

public enum TipoENUM {
	PROBLEMA("problema"),
	DICA("dica"),
	SOLUCAO("solucao");
	
	private String tipo;
	
	TipoENUM(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
}
