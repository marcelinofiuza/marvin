package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum Natureza da Conta
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
public enum Natureza {

	ATIVO("Ativo"),
	PASSIVO("Passivo"),
	DESPESA("Despesa"), 
	RECEITA("Receita");
	
	private String descricao;

	Natureza(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}	
}
