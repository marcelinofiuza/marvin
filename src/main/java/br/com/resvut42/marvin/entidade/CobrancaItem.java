package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

/****************************************************************************
 * Entidade Cobrança Item Desenvolvido por :
 * 
 * @author Bob-Odin - 31/03/2017
 ****************************************************************************/
@Entity
public class CobrancaItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idItem;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCobranca")
	private Cobranca cobranca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@Range(min=0, max=31, message="O dia deve ser entre 0 e 31!")
	private Integer diaVencimento;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;

	@NumberFormat(pattern = "#,##0.000000")
	@Column(precision = 19, scale = 6)
	private BigDecimal fracao1;

	@NumberFormat(pattern = "#,##0.000000")
	@Column(precision = 19, scale = 6)
	private BigDecimal fracao2;

	@NumberFormat(pattern = "#,##0.000000")
	@Column(precision = 19, scale = 6)
	private BigDecimal fracao3;

	public CobrancaItem(){
		diaVencimento = 0;
		valor = new BigDecimal(0);
		fracao1 = new BigDecimal(0);
		fracao2 = new BigDecimal(0);
		fracao3 = new BigDecimal(0);
	}
		
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getFracao1() {
		return fracao1;
	}

	public void setFracao1(BigDecimal fracao1) {
		this.fracao1 = fracao1;
	}

	public BigDecimal getFracao2() {
		return fracao2;
	}

	public void setFracao2(BigDecimal fracao2) {
		this.fracao2 = fracao2;
	}

	public BigDecimal getFracao3() {
		return fracao3;
	}

	public void setFracao3(BigDecimal fracao3) {
		this.fracao3 = fracao3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CobrancaItem))
			return false;
		CobrancaItem other = (CobrancaItem) obj;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		return true;
	}

}
