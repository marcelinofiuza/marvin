package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Fornecedor;
import br.com.resvut42.marvin.repositorio.RepFornecedor;

/****************************************************************************
 * Classe Serviço Regras de negócio do Fornecedor Desenvolvido por :
 * 
 * @author Gustavo - 30/03/2017
 ****************************************************************************/
@Service
public class SerFornecedor {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/	
	@Autowired
	RepFornecedor repFornecedor;

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Fornecedor fornecedor) throws Exception {
		try {
			repFornecedor.save(fornecedor);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Fornecedor fornecedor) throws Exception {
		try {
			repFornecedor.delete(fornecedor);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public Fornecedor buscarPorId(Long id) {
		return repFornecedor.findOne(id);
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Fornecedor> listarTodos() {
		return repFornecedor.findAll();
	}

}
