package br.com.resvut42.marvin.cnabRetorno;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import br.com.resvut42.marvin.util.R42Data;

/****************************************************************************
 * Classe para letura retorno CNAB Bradesco 400
 * 
 * @author: Bob-Odin - 28/04/2017
 ****************************************************************************/
public class CnabRetorno237400 extends CnabRetorno {

	public CnabRetorno237400() {
		cabecalho = new RetornoCabecalho();
		listaItens = new ArrayList<RetornoItem>();
	}

	@Override
	protected void processaCabecalho() throws Exception {
		// TODO Auto-generated method stub
		try {
			BufferedReader bufferedReader = getBuffer();
			String linha;
			while ((linha = bufferedReader.readLine()) != null) {

				if (linha.substring(0, 1).equals("0")) {
					String buffer = String.valueOf(linha.length());
					cabecalho.setArquivo(getNomeArquivo());
					cabecalho.setCodigoEmpresa(linha.substring(26, 46));
					cabecalho.setNomeEmpresa(linha.substring(46, 76));
					cabecalho.setFebraban(converteFebraban(linha.substring(76, 79)));
					cabecalho.setLayoutCnab(converteLayoutCnab(buffer));
					cabecalho.setDataGravacao(R42Data.stringToDate(linha.substring(94, 100)));
					cabecalho.setDataCredito(R42Data.stringToDate(linha.substring(379, 385)));
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception("Erro ao ler cabeçalho do arquivo");
		}
	}

	@Override
	protected void processaItens() throws Exception {
		// TODO Auto-generated method stub
		int registro = 0;
		listaItens.clear();
		try {
			BufferedReader bufferedReader = getBuffer();
			String linha;
			while ((linha = bufferedReader.readLine()) != null) {

				registro++;
				if (linha.substring(0, 1).equals("1")) {

					RetornoItem item = new RetornoItem();
					item.setNumeroBoleto(StringUtils.rightPad(linha.substring(37, 62).trim(), 25, "0"));
					item.setNumeroTitulo(linha.substring(70, 82));
					item.setCodRetorno01(linha.substring(108, 110));
					item.setCodRetorno02(linha.substring(318, 320));
					item.setVctoRetorno(R42Data.stringToDate(linha.substring(146, 152)));
					item.setValorTitulo(converteValor(linha.substring(152, 165)));
					item.setValorJuros(converteValor(linha.substring(266, 279)));
					item.setValorDescontos(converteValor(linha.substring(240, 253)));
					item.setValorPago(converteValor(linha.substring(253, 266)));
					item.setDataPagamento(R42Data.stringToDate(linha.substring(295, 301)));

					listaItens.add(item);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Erro ao ler registro " + registro);
		}
	}

}
