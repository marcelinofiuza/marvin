package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Usuario;

/****************************************************************************/
// Classe Repositório da entidade Usuário
// Desenvolvido por : Bob-Odin 
// Criado em 30/01/2017 
/****************************************************************************/

@Repository
public interface RepUsuario extends JpaRepository<Usuario, Long> {

	/****************************************************************************/
	// Metodo para retornar pela credencial
	/****************************************************************************/
	public Usuario findByCredencial(String credencial);

}
