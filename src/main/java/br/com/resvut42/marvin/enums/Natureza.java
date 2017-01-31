package br.com.resvut42.marvin.enums;

/****************************************************************************/
// Enumerador Natureza da Conta
// Desenvolvido por : Bob-Odin
// Criado em 31/01/2017 
/****************************************************************************/

public enum Natureza {

	ATIVO("Ativo"),
	PASSIVO("Passivo"),
	DESPESA("Despesas - Devedora de resultado"), 
	RECEITA("Receita - Credora de resultado");
	
	private String descricao;

	Natureza(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}	
}
