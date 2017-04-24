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
	 * Retorna se existe algum fornecedor cadastro
	 ****************************************************************************/
	public boolean existeFornecedor() {
		if (repFornecedor.count() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Fornecedor fornecedor) throws Exception {
		try {
			ajustarDados(fornecedor);
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
	
	/****************************************************************************
	 * Metodo para validar dados antes de salvar
	 ****************************************************************************/
	
	private void ajustarDados(Fornecedor fornecedor) {

		// Seta null no cnpj quando estiver em branco para validação
		if (fornecedor.getCnpj() != null && fornecedor.getCnpj().isEmpty()) {
			fornecedor.setCnpj(null);
		}

		// Seta null no cpf quando estiver em branco para validação
		if (fornecedor.getCpf() != null && fornecedor.getCpf().isEmpty()) {
			fornecedor.setCpf(null);
		}

	}
	
	/****************************************************************************
	 * Retornar uma lista de fornecedor filtrando por Like RazaoSocial e fantasia
	 ****************************************************************************/
	public List<Fornecedor> listarPorRazaoSocialOuFantasia(String razaoSocial, String fantasia) {
		return repFornecedor.findByRazaoSocialContainingAndFantasiaContaining(razaoSocial, fantasia);
	}

	/****************************************************************************
	 * Retornar um fornecedor buscando pela fantasia
	 ****************************************************************************/
	public Fornecedor buscarPorFantasia(String fantasia) {
		return repFornecedor.findByFantasia(fantasia);
	}

}
