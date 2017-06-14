package br.com.resvut42.marvin.relatorio;

import java.util.List;

import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Relatórido de extrato bancário
 * 
 * @author: Bob-Odin - 13/06/2017
 ****************************************************************************/
public class RelExtrato {

	public Relatorio relatorio;

	/****************************************************************************
	 * Construtor
	 ****************************************************************************/
	public RelExtrato() {
		relatorio = new Relatorio();
	}

	/****************************************************************************
	 * Seta lista de lancamentos a ser impresso
	 ****************************************************************************/
	public void setLista(List<BancoLcto> lancamentos) {
		relatorio.setListaDados(lancamentos);
	}

	/****************************************************************************
	 * Seta Parametros no relatório
	 ****************************************************************************/
	public void setParametros(String parametro, Object valor) {
		relatorio.setParametro(parametro, valor);
	}

	/****************************************************************************
	 * Gera relatório de clientes
	 ****************************************************************************/
	public void gerar() throws Exception {
		relatorio.setArquivoJrxml("Extrato");
		relatorio.setParametro("empresa", R42Util.resgataEmpresa().getFantasia());
		relatorio.geraRelatorio();
	}
}
