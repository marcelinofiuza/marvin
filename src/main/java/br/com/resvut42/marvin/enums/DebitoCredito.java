package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum DébitoCrédito
 * 
 * @author Bob-Odin - 22/03/2017
 ****************************************************************************/
public enum DebitoCredito {
	
	DEBITO("Débito",-1),	
	CREDITO("Crédito",1);

	private String descricao;
	private int multiplicador;

	DebitoCredito(String descricao, int multiplicador) {
		this.descricao = descricao;
		this.multiplicador = multiplicador;
	}

	public String getDescricao() {
		return this.descricao;
	}
	
	public int getMultiplicador(){
		return multiplicador;
	}
	
}
