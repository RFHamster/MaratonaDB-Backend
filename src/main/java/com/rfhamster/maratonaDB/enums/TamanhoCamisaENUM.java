package com.rfhamster.maratonaDB.enums;

public enum TamanhoCamisaENUM {
	BABYPP("PP_babylook"),
	BABYP("P_babylook"),
	BABYM("M_babylook"),
	BABYG("G_babylook"),
	BABYGG("GG_babylook"),
	BABYXG("XG_babylook"),
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
