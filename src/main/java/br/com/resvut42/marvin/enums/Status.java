package br.com.resvut42.marvin.enums;

/****************************************************************************/
// Enumerador de Ativo e Inativo
// Desenvolvido por : Bob-Odin 
// Criado em 31/01/2017 
/****************************************************************************/

public enum Status {

	ATIVA("Ativa"), 
	INATIVA("Inativa");

	private String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
