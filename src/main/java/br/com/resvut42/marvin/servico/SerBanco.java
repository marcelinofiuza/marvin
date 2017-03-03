package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.repositorio.RepBanco;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
public class SerBanco {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBanco repBanco;

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Banco banco) throws Exception {
		try {
			repBanco.save(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Banco banco) throws Exception {
		try {
			repBanco.delete(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Banco> listarTodos() {
		return repBanco.findAll();
	}

}
