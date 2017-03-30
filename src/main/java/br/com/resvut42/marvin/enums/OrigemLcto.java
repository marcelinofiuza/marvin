package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum Origem Lancamento
 * 
 * @author Bob-Odin - 28/03/2017
 ****************************************************************************/
public enum OrigemLcto {

	BCO("Banco"), 
	TRF("Transferência"), 
	DCR("Ctas Receber"), 
	DCP("Ctas Pagar");

	private String descricao;

	OrigemLcto(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}	
}
