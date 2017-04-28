package br.com.resvut42.marvin.cnabRemessa;

import java.util.Date;

import br.com.resvut42.marvin.entidade.BoletoItem;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Classe para geração arquivo CNAB Bradesco
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public class Cnab237400 extends CnabRemessa {

	public Cnab237400() {
		this.boleto = null;
		this.carteira = null;
		this.empresa = R42Util.resgataEmpresa();
		this.arquivo = null;
	}
				
	@Override
	protected void header() {
		// TODO Auto-generated method stub

		String linha = "";
		linha = linha + getZero(1);
		linha = linha + "1";
		linha = linha + "REMESSA";
		linha = linha + "01";
		linha = linha + String.format("%-15s", "COBRANCA");
		linha = linha + getCodigoMestre(20);
		linha = linha + getNomeEmpresa(30);
		linha = linha + getCodigoFebraban();
		linha = linha + String.format("%-15s", "BRADESCO");
		linha = linha + configuraData(new Date());
		linha = linha + getSpace(8);
		linha = linha + "MX";
		linha = linha + getSequenciaArquivo();
		linha = linha + getSpace(277);
		linha = linha + getProximaLinha();
		linha = linha + "\n";
		
		arquivo.printf(linha);
	}

	@Override
	protected void detalhe() {
		// TODO Auto-generated method stub
		
		String linha = "";
		
		for (BoletoItem item : boleto.getItens()) {
			
			setBoletoItem(item);
			
			linha = "";
			linha = linha + "1";
			linha = linha + getZero(5);
			linha = linha + getSpace(1);
			linha = linha + getZero(5);
			linha = linha + getZero(7);
			linha = linha + getSpace(1);
			linha = linha + getIdentEmpresa(17);
			linha = linha + getNumeroBoleto();
			linha = linha + getZero(3);
			linha = linha + getZero(1);
			linha = linha + getZero(4);
			linha = linha + getNossoNumero();
			linha = linha + getZero(10);
			linha = linha + "1";
			linha = linha + "N";
			linha = linha + getSpace(10);
			linha = linha + getSpace(1);
			linha = linha + "2";
			linha = linha + getSpace(2);
			linha = linha + "01";
			linha = linha + getDocumento();
			linha = linha + getVctoTitulo();
			linha = linha + getValorTitulo();
			linha = linha + getCodigoFebraban();
			linha = linha + getZero(5);
			linha = linha + "01";
			linha = linha + "N";
			linha = linha + getDataEmissao();
			linha = linha + getZero(2);
			linha = linha + getZero(2);
			linha = linha + getZero(13);
			linha = linha + getZero(6);
			linha = linha + getZero(13);
			linha = linha + getZero(13);
			linha = linha + getZero(13);
			linha = linha + getInscricao();
			linha = linha + getNomeCliente(40);
			linha = linha + getEnderecoCliente(40);
			linha = linha + getSpace(12);
			linha = linha + getCepEndereco();
			linha = linha + getSpace(60);
			linha = linha + getProximaLinha();
			linha = linha + "\n";
			
			arquivo.printf(linha);
			
			linha = "";
			linha = linha + "2";
			linha = linha + getMensagem("1", 80);
			linha = linha + getMensagem("2", 80);
			linha = linha + getMensagem("3", 80);
			linha = linha + getMensagem("4", 80);
			linha = linha + getSpace(45);
			linha = linha + getIdentEmpresa(16);
			linha = linha + getNossoNumero();
			linha = linha + getProximaLinha();
			linha = linha + "\n";
			
			arquivo.printf(linha);			
		}	
	}

	@Override
	protected void footer() {
		// TODO Auto-generated method stub
		String linha = "";
		linha = linha + "9";
		linha = linha + getSpace(393);
		linha = linha + getProximaLinha();
		linha = linha + "\n";
		
		arquivo.printf(linha);
	}

}
