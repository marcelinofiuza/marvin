package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Receber;
import br.com.resvut42.marvin.repositorio.RepReceber;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos a Receber:
 * 
 * @author Bob-Odin - 20/04/2017
 ****************************************************************************/
@Service
public class SerReceber {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepReceber repReceber;

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Receber receber) throws Exception {
		try {
			repReceber.save(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar Lista
	 ****************************************************************************/
	public void salvar(List<Receber> listaReceber) throws Exception {
		try {
			repReceber.save(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Receber receber) throws Exception {
		try {
			validaExcluir(receber);
			repReceber.delete(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir uma lista de receber
	 ****************************************************************************/
	public void excluir(List<Receber> listaReceber) throws Exception {
		try {
			for (Receber receber : listaReceber) {
				validaExcluir(receber);
			}
			repReceber.delete(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar a exclusão de receber
	 ****************************************************************************/
	public void validaExcluir(Receber receber) throws Exception {
		if(receber.getBaixas().size() != 0){
			throw new Exception("o documento "+receber.getDocumento()+" já está com baixa!");
		}
	}

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por cliente
	 ****************************************************************************/
	public List<Receber> listarPorCliente(Cliente cliente) {
		return repReceber.findByCliente(cliente);
	}

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por boleto
	 ****************************************************************************/
	public List<Receber> listarPorBoleto(Boleto boleto) {
		return repReceber.findByBoleto(boleto);
	}
}
