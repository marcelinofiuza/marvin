package br.com.resvut42.marvin.cnabRetorno;

import java.math.BigDecimal;
import java.util.Date;

import br.com.resvut42.marvin.entidade.Receber;

public class RetornoItem {

	private String numeroBoleto;
	private String numeroTitulo;
	private String codRetorno01;
	private String codRetorno02;
	private Date vctoRetorno;
	private BigDecimal valorTitulo;
	private BigDecimal valorJuros;
	private BigDecimal valorDescontos;
	private BigDecimal valorPago;
	private Date dataPagamento;
	private Receber receber;

	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}

	public String getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}

	public String getCodRetorno01() {
		return codRetorno01;
	}

	public void setCodRetorno01(String codRetorno01) {
		this.codRetorno01 = codRetorno01;
	}

	public String getCodRetorno02() {
		return codRetorno02;
	}

	public void setCodRetorno02(String codRetorno02) {
		this.codRetorno02 = codRetorno02;
	}

	public String getRetorno() {
		if (codRetorno01 == "02") {
			return "ENTRADA CONFIRMADA " + codRetorno02;
		} else if (codRetorno01 == "03") {
			return "ENTRADA REJEITADA  " + codRetorno02;
		} else if (codRetorno01 == "06") {
			return "LIQUIDAÇÃO EFETUADA  " + codRetorno02;
		} else {
			return "VERIFICAR CODIGO " + codRetorno01 + " CODIGO " + codRetorno02;
		}
	}

	public Date getVctoRetorno() {
		return vctoRetorno;
	}

	public void setVctoRetorno(Date vctoRetorno) {
		this.vctoRetorno = vctoRetorno;
	}

	public BigDecimal getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(BigDecimal valorTitulo) {
		this.valorTitulo = valorTitulo;
	}

	public BigDecimal getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros) {
		this.valorJuros = valorJuros;
	}

	public BigDecimal getValorDescontos() {
		return valorDescontos;
	}

	public void setValorDescontos(BigDecimal valorDescontos) {
		this.valorDescontos = valorDescontos;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Receber getReceber() {
		return receber;
	}

	public void setReceber(Receber receber) {
		this.receber = receber;
	}

	public String getStatus() {
		if (receber == null) {
			return "erro";
		} else {
			if(receber.getSaldo().compareTo(valorTitulo) < 0){
				return "alerta";
			}else{
				return "ok";
			}
		}
	}

}
