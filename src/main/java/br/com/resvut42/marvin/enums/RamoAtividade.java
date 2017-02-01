package br.com.resvut42.marvin.enums;

/****************************************************************************/
// Enumerador Ramo de Atividade da Empresa
// Desenvolvido por : Bob-Odin 
// Criado em 01/03/2017 
/****************************************************************************/

public enum RamoAtividade {

	AUTO_SERVICO("Auto serviço"), 
	COMERCIO("Comércio"), 	 
	COMERCIO_REGISTRADORA("Comércio com registradora"),
	INDUSTRIA("Industria"),
	MICRO_EMPRESA("Micro empresa"), 
	PRESTACAO_SERVICO("Prestação de serviço");

	private String descricao;

	RamoAtividade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
