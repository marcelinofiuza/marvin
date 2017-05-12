package br.com.resvut42.marvin.cnabRetorno;

import java.util.ArrayList;

/****************************************************************************
 * Classe para letura retorno CNAB Padrão 000 - selecionar Header
 * 
 * @author: Bob-Odin - 30/04/2017
 ****************************************************************************/
public class CnabRetorno000000 extends CnabRetorno {

	public CnabRetorno000000() {
		cabecalho = new RetornoCabecalho();
		listaItens = new ArrayList<RetornoItem>();
	}

	@Override
	protected void processaCabecalho() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void processaItens() throws Exception {
		// TODO Auto-generated method stub
	}

}
