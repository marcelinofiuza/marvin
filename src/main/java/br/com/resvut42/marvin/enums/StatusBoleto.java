package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum StatusBoleto para situação do Boleto
 * 
 * @author Bob-Odin - 07/04/2017
 ****************************************************************************/
public enum StatusBoleto {

	ABERTO("Aberto"), 
	PROVISIONADO("Provisionado");

	private String descricao;

	private StatusBoleto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
