package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Usuario;
import br.com.resvut42.marvin.repositorio.RepUsuario;

/****************************************************************************/
// Classe Serviço Regras de negócio do Usuário
// Desenvolvido por : Bob-Odin 
// Criado em 30/01/2017 
/****************************************************************************/

@Service
public class SerUsuario {

	/****************************************************************************/
	// Variaveis e Dependências
	/****************************************************************************/
	@Autowired
	RepUsuario repUsuario;

	/****************************************************************************/
	// Metodo para Validar e salvar
	/****************************************************************************/
	public void salvar(Usuario usuario) throws Exception {
		try {

			repUsuario.save(usuario);

		} catch (Exception e) {

			throw new Exception(e.getMessage());

		}
	}

	/****************************************************************************/
	// Metodo para Validar e excluir
	/****************************************************************************/
	public void excluir(Usuario usuario) throws Exception {
		try {

			repUsuario.delete(usuario);

		} catch (Exception e) {

			throw new Exception(e.getMessage());

		}
	}

	/****************************************************************************/
	// Metodo para Listar todos os registros
	/****************************************************************************/
	public List<Usuario> ListarTodos() {

		return repUsuario.findAll();

	}

	/****************************************************************************/
	// Metodo para buscar por credencial
	/****************************************************************************/
	public Usuario buscarPorCredencial(String credencial) {

		return repUsuario.findByCredencial(credencial);

	}

}
