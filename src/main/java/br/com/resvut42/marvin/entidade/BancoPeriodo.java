package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.resvut42.marvin.enums.DebitoCredito;
import br.com.resvut42.marvin.util.R42Data;

/****************************************************************************
 * Entidade Periodo Banco, para gravação de saldos e fechamento contabil
 * 
 * @author Bob-Odin - 18/03/2017
 ****************************************************************************/
@Entity
public class BancoPeriodo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPeriodo;

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

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal debito;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal credito;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldoFinal;

	private boolean abertura;

	private boolean fechado;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idPeriodo")
	@OrderBy("dataLcto")
	private List<BancoLcto> lancamentos = new ArrayList<BancoLcto>();

	public BancoPeriodo() {
		this.saldoInicial = new BigDecimal(0);
		this.credito = new BigDecimal(0);
		this.debito = new BigDecimal(0);
	}

	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
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

	public String getDataPeriodo() {
		return R42Data.dateToString(dataInicio) + " - " + R42Data.dateToString(dataFinal);
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
		if (debito.compareTo(new BigDecimal(0)) == 0) {
			somaDebitoCredito();
		}
		return debito;
	}

	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}

	public BigDecimal getCredito() {
		if (credito.compareTo(new BigDecimal(0)) == 0) {
			somaDebitoCredito();
		}
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	public BigDecimal getSaldoFinal() {
		BigDecimal sldFinal = saldoInicial.add(getCredito());
		sldFinal = sldFinal.subtract(getDebito());
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

	public List<BancoLcto> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<BancoLcto> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public void addLancamento(BancoLcto bancoLcto) {
		this.lancamentos.add(bancoLcto);
	}

	private void somaDebitoCredito() {
		this.credito = new BigDecimal(0);
		this.debito = new BigDecimal(0);		
		for (BancoLcto bancoLcto : lancamentos) {
			if (bancoLcto.getTipoLcto() == DebitoCredito.DEBITO) {
				this.debito = this.debito.add(bancoLcto.getValorLcto());
			} else {
				this.credito = this.credito.add(bancoLcto.getValorLcto());
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPeriodo ^ (idPeriodo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BancoPeriodo))
			return false;
		BancoPeriodo other = (BancoPeriodo) obj;
		if (idPeriodo != other.idPeriodo)
			return false;
		return true;
	}

}
