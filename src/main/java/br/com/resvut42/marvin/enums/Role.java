package br.com.resvut42.marvin.enums;

/****************************************************************************/
// Enumerador de Roles de perfil
// Desenvolvido por : Bob-Odin
// Criado em 30/01/2017 
/****************************************************************************/

public enum Role {
	
	ADMIN("Administrador"),
	USUARIO("Usuario");
	
	private String descricao;

	Role(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}	
}
