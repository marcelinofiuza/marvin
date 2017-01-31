package br.com.resvut42.marvin.enums;

/****************************************************************************/
// Enumerador do Tipo de Conta contábil
// Desenvolvido por : Bob-Odin 
// Criado em 31/01/2017 
/****************************************************************************/

public enum TipoConta {

	ANALITICA("Analítica"), 
	SINTETICA("Sintética");

	private String descricao;

	TipoConta(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
