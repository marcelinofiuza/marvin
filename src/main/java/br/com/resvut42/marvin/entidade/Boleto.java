package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.resvut42.marvin.enums.StatusBoleto;

/****************************************************************************
 * Entidade Boleto Desenvolvido por :
 * 
 * @author Bob-Odin - 07/04/2017
 ****************************************************************************/
@Entity
public class Boleto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBoleto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCobranca")
	private Cobranca cobranca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lancamento;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal base1;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal base2;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal base3;

	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private StatusBoleto statusBoleto;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idBoleto")
	private List<BoletoItem> itens = new ArrayList<BoletoItem>();

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
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

	public StatusBoleto getStatusBoleto() {
		return statusBoleto;
	}

	public void setStatusBoleto(StatusBoleto statusBoleto) {
		this.statusBoleto = statusBoleto;
	}

	public List<BoletoItem> getItens() {
		return itens;
	}

	public void setItens(List<BoletoItem> itens) {
		this.itens = itens;
	}

	public void addItem(BoletoItem item) {
		item.setBoleto(this);
		itens.add(item);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBoleto == null) ? 0 : idBoleto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Boleto))
			return false;
		Boleto other = (Boleto) obj;
		if (idBoleto == null) {
			if (other.idBoleto != null)
				return false;
		} else if (!idBoleto.equals(other.idBoleto))
			return false;
		return true;
	}

}
