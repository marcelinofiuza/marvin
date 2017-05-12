package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/****************************************************************************
 * Entidade Pagar Desenvolvido por :
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/

@Entity
public class Pagar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPagar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idFornecedor")
	private Fornecedor fornecedor;
	
	@Column(length = 15)
	private String documento;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lancamento;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date vencimento;

	@NotNull(message = "Informar o valor do documento!")
	@DecimalMin(value = "0.01", message = "Não pode ser menor que 0,01")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@Column(length = 250)
	private String historico;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idPagar")
	@OrderBy("dataLcto")
	private List<BancoLcto> baixas = new ArrayList<BancoLcto>();

	public boolean isQuitado() {
		if(getSaldo().compareTo(new BigDecimal(0))==0){
			return true;
		}else{
			return false;
		}
	}	
	
	public Long getIdPagar() {
		return idPagar;
	}

	public void setIdPagar(Long idPagar) {
		this.idPagar = idPagar;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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

	public List<BancoLcto> getBaixas() {
		return baixas;
	}

	public void setBaixas(List<BancoLcto> baixas) {
		this.baixas = baixas;
	}

	public void addBaixa(BancoLcto bancoLcto){
		bancoLcto.setPagar(this);
		this.baixas.add(bancoLcto);
	}

	public BigDecimal getPago() {
		BigDecimal totalPago = new BigDecimal(0);
		for (BancoLcto bancoLcto : baixas) {
			totalPago = totalPago.add(bancoLcto.getValorLctoConvertido());
		}
		return totalPago;
	}

	public BigDecimal getAcrescimos() {
		BigDecimal totalAcrescimos = new BigDecimal(0);
		for (BancoLcto bancoLcto : baixas) {
			totalAcrescimos = totalAcrescimos.add(bancoLcto.getJuros());
			totalAcrescimos = totalAcrescimos.add(bancoLcto.getMulta());
		}
		return totalAcrescimos;
	}	
	
	public BigDecimal getDescontos() {
		BigDecimal totalDescontos = new BigDecimal(0);
		for (BancoLcto bancoLcto : baixas) {
			totalDescontos = totalDescontos.add(bancoLcto.getDesconto());
		}
		return totalDescontos;
	}		
	
	public BigDecimal getSaldo() {
		BigDecimal saldo = new BigDecimal(0);
		saldo = valor.subtract(getPago());
		saldo = saldo.subtract(getDescontos());
		saldo = saldo.add(getAcrescimos());
		return saldo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPagar == null) ? 0 : idPagar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagar other = (Pagar) obj;
		if (idPagar == null) {
			if (other.idPagar != null)
				return false;
		} else if (!idPagar.equals(other.idPagar))
			return false;
		return true;
	}

}
