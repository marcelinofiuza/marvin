package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum Status Ativa ou Inativa
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
public enum AtivaItativa {

	ATIVA("Ativa"), 
	INATIVA("Inativa");

	private String descricao;

	AtivaItativa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
