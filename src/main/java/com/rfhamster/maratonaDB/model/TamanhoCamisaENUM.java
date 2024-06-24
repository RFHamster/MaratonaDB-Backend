package com.rfhamster.maratonaDB.model;

public enum TamanhoCamisaENUM {
	PP("PP"),
	P("P"),
	M("M"),
	G("G"),
	GG("GG"),
	XG("XG");
	
	private String tamanho;
	
	TamanhoCamisaENUM(String tamanho) {
		this.tamanho = tamanho;
	}
	
	public String getTamanho() {
		return tamanho;
	}
}
