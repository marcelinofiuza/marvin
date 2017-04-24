package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/****************************************************************************
 * Entidade Pagar Desenvolvido por :
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/

@Entity
public class Pagar implements Serializable{

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
	
	private boolean quitado;
	
	@Column(length = 15)
	private double valorTitulo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;
	
	@Column(length = 250)
	private String historico;

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

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public double getValorTitulo() {
		return valorTitulo;
	}

	public void setValorTitulo(double valorTitulo) {
		this.valorTitulo = valorTitulo;
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
