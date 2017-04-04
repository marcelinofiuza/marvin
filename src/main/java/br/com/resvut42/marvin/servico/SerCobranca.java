package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.repositorio.RepCobranca;

/****************************************************************************
 * Classe Serviço Regras de negócio do Cobranca Desenvolvido por :
 * 
 * @author Marcelino - 02/04/2017
 ****************************************************************************/
@Service
public class SerCobranca {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepCobranca repCobranca;
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Cobranca cobranca) throws Exception {
		try {
			repCobranca.save(cobranca);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Cobranca cobranca) throws Exception {
		try {
			repCobranca.delete(cobranca);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Cobranca> listarTodos() {
		return repCobranca.findAll();
	}	
}
