package br.com.resvut42.marvin.relatorio;

import java.util.List;

import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Relatórido Lista de Clientes
 * 
 * @author: Bob-Odin - 07/06/2017
 ****************************************************************************/
public class RelCliente {

	public Relatorio relatorio;

	/****************************************************************************
	 * Construtor
	 ****************************************************************************/
	public RelCliente() {
		relatorio = new Relatorio();
	}

	/****************************************************************************
	 * Seta lista de Clientes a ser impresso
	 ****************************************************************************/
	public void setLista(List<Cliente> listaClientes) {
		relatorio.setListaDados(listaClientes);
	}

	/****************************************************************************
	 * Gera relatório de clientes
	 ****************************************************************************/
	public void gerar() throws Exception {
		relatorio.setArquivoJrxml("Clientes");
		relatorio.setParametro("empresa", R42Util.resgataEmpresa().getFantasia());
		relatorio.geraRelatorio();
	}

}
