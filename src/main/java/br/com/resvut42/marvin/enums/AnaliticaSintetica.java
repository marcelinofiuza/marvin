package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum AnaliticaSintetica
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
public enum AnaliticaSintetica {

	ANALITICA("Analítica"), 
	SINTETICA("Sintética");

	private String descricao;

	AnaliticaSintetica(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
