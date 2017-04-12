package br.com.resvut42.marvin.estrutura;

import java.math.BigDecimal;
import java.util.Date;

import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.BoletoItem;
import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.entidade.CobrancaItem;
import br.com.resvut42.marvin.enums.StatusBoleto;
import br.com.resvut42.marvin.util.R42Data;

public class CalculoBoleto {

	private Cobranca cobranca;
	private Date vencimento;
	private BigDecimal base1;
	private BigDecimal base2;
	private BigDecimal base3;

	public CalculoBoleto() {
		base1 = new BigDecimal(0);
		base2 = new BigDecimal(0);
		base3 = new BigDecimal(0);
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public BigDecimal getBase1() {
		return base1;
	}

	public void setBase1(BigDecimal base1) {
		this.base1 = base1;
	}

	public BigDecimal getBase2() {
		return base2;
	}

	public void setBase2(BigDecimal base2) {
		this.base2 = base2;
	}

	public BigDecimal getBase3() {
		return base3;
	}

	public void setBase3(BigDecimal base3) {
		this.base3 = base3;
	}

	/****************************************************************************
	 * Retorna o Boleto já caluculado pela cobrança
	 ****************************************************************************/
	public Boleto getBoleto() {
		Boleto boleto = new Boleto();

		boleto.setStatusBoleto(StatusBoleto.ABERTO);
		boleto.setLancamento(R42Data.dataAtual());
		boleto.setConta(cobranca.getConta());
		boleto.setInsLinha01(cobranca.getInsLinha01());
		boleto.setInsLinha02(cobranca.getInsLinha02());
		boleto.setInsLinha03(cobranca.getInsLinha03());
		boleto.setInsLinha04(cobranca.getInsLinha04());
		boleto.setInsLinha05(cobranca.getInsLinha05());
		boleto.setInsLinha06(cobranca.getInsLinha06());
		boleto.setInsLinha07(cobranca.getInsLinha07());
		boleto.setInsLinha08(cobranca.getInsLinha08());

		long nextItem = 9000000;
		for (CobrancaItem item : cobranca.getItens()) {
			
			BoletoItem boletoItem = new BoletoItem();
			nextItem++;

			boletoItem.setIdItem(nextItem);
			boletoItem.setCliente(item.getCliente());
			boletoItem.setVencimento(R42Data.montaData(vencimento, item.getDiaVencimento()));
			boletoItem.setValor0(item.getValor());
			boletoItem.setValor1(calculaValor(item.getFracao1(), base1));
			boletoItem.setValor2(calculaValor(item.getFracao2(), base2));
			boletoItem.setValor3(calculaValor(item.getFracao3(), base3));

			boleto.addItem(boletoItem);
		}

		return boleto;
	}

	private BigDecimal calculaValor(BigDecimal fracao, BigDecimal base) {
		BigDecimal valor = base.multiply(fracao);
		valor = valor.divide(new BigDecimal(100));
		return valor;
	}

}
