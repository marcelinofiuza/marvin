package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum Layout CNAB
 * 
 * @author Bob-Odin - 27/04/2017
 ****************************************************************************/
public enum LayoutCnab {
	
	L400("400 Posições"), 
	L240("240 Posições");

	private String descricao;

	LayoutCnab(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
