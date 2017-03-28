package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.resvut42.marvin.enums.DebitoCredito;
import br.com.resvut42.marvin.enums.OrigemLcto;

/****************************************************************************
 * Entidade BancoLcto, para gravação dos lançamentos contábeis
 * 
 * @author Bob-Odin - 22/03/2017
 ****************************************************************************/
@Entity
public class BancoLcto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLcto;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idPeriodo")
	private BancoPeriodo bancoPeriodo;

	@Enumerated(EnumType.STRING)
	@Column(length = 3)
	private OrigemLcto origemLcto;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataLcto;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private DebitoCredito tipoLcto;

	@NotEmpty
	@Column(length = 15)
	private String documento;

	private Boolean cheque;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorBase;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal juros;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal multa;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal desconto;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.01", message = "Não pode ser menor que 0,01")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorLcto;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@NotEmpty
	@Column(length = 250)
	private String historico;

	public BancoLcto() {
		this.valorBase = new BigDecimal(0);
		this.juros = new BigDecimal(0);
		this.multa = new BigDecimal(0);
		this.desconto = new BigDecimal(0);
		this.valorLcto = new BigDecimal(0);
	}

	public Long getIdLcto() {
		return idLcto;
	}

	public void setIdLcto(Long idLcto) {
		this.idLcto = idLcto;
	}

	public BancoPeriodo getBancoPeriodo() {
		return bancoPeriodo;
	}

	public void setBancoPeriodo(BancoPeriodo bancoPeriodo) {
		this.bancoPeriodo = bancoPeriodo;
	}

	public OrigemLcto getOrigemLcto() {
		return origemLcto;
	}

	public void setOrigemLcto(OrigemLcto origemLcto) {
		this.origemLcto = origemLcto;
	}

	public Date getDataLcto() {
		return dataLcto;
	}

	public void setDataLcto(Date dataLcto) {
		this.dataLcto = dataLcto;
	}

	public DebitoCredito getTipoLcto() {
		return tipoLcto;
	}

	public void setTipoLcto(DebitoCredito tipoLcto) {
		this.tipoLcto = tipoLcto;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Boolean getCheque() {
		return cheque;
	}

	public void setCheque(Boolean cheque) {
		this.cheque = cheque;
	}

	public BigDecimal getValorBase() {
		valorBase = valorLcto;
		valorBase.add(desconto);
		valorBase.subtract(juros);
		valorBase.subtract(multa);
		return valorBase;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getValorLcto() {
		return valorLcto;
	}

	public BigDecimal getValorLctoConvertido() {
		return valorLcto.multiply(BigDecimal.valueOf(tipoLcto.getMultiplicador()));
	}

	public void setValorLcto(BigDecimal valorLcto) {
		this.valorLcto = valorLcto;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLcto == null) ? 0 : idLcto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BancoLcto))
			return false;
		BancoLcto other = (BancoLcto) obj;
		if (idLcto == null) {
			if (other.idLcto != null)
				return false;
		} else if (!idLcto.equals(other.idLcto))
			return false;
		return true;
	}

}
