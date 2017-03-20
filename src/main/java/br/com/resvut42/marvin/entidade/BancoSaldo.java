package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/****************************************************************************
 * Entidade Saldo Banco, para gravação de saldos e fechamento contabil
 * 
 * @author Bob-Odin - 18/03/2017
 ****************************************************************************/
@Entity
public class BancoSaldo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idSaldo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFinal;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldoInicial;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal debito;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal credito;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldoFinal;

	private boolean abertura;
	
	private boolean fechado;

	public long getIdSaldo() {
		return idSaldo;
	}

	public void setIdSaldo(long idSaldo) {
		this.idSaldo = idSaldo;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getDebito() {
		return debito;
	}

	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}

	public BigDecimal getCredito() {
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	public BigDecimal getSaldoFinal() {
		BigDecimal sldFinal = saldoInicial.add(credito);
		sldFinal = sldFinal.subtract(debito);
		return sldFinal;
	}

	public boolean isAbertura() {
		return abertura;
	}

	public void setAbertura(boolean abertura) {
		this.abertura = abertura;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idSaldo ^ (idSaldo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BancoSaldo))
			return false;
		BancoSaldo other = (BancoSaldo) obj;
		if (idSaldo != other.idSaldo)
			return false;
		return true;
	}

}
