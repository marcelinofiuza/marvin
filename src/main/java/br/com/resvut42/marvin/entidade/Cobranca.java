package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/****************************************************************************
 * Entidade Cobrança Desenvolvido por :
 * 
 * @author Bob-Odin - 31/03/2017
 ****************************************************************************/
@Entity
public class Cobranca implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCobranca;

	@Column(length = 50)
	private String descricao;

	@Column(length = 80)
	private String insLinha01;

	@Column(length = 80)
	private String insLinha02;

	@Column(length = 80)
	private String insLinha03;

	@Column(length = 80)
	private String insLinha04;

	@Column(length = 80)
	private String insLinha05;

	@Column(length = 80)
	private String insLinha06;

	@Column(length = 80)
	private String insLinha07;

	@Column(length = 80)
	private String insLinha08;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idCobranca")
	private List<CobrancaItem> itens = new ArrayList<CobrancaItem>();

	public Long getIdCobranca() {
		return idCobranca;
	}

	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getInsLinha01() {
		return insLinha01;
	}

	public void setInsLinha01(String insLinha01) {
		this.insLinha01 = insLinha01;
	}

	public String getInsLinha02() {
		return insLinha02;
	}

	public void setInsLinha02(String insLinha02) {
		this.insLinha02 = insLinha02;
	}

	public String getInsLinha03() {
		return insLinha03;
	}

	public void setInsLinha03(String insLinha03) {
		this.insLinha03 = insLinha03;
	}

	public String getInsLinha04() {
		return insLinha04;
	}

	public void setInsLinha04(String insLinha04) {
		this.insLinha04 = insLinha04;
	}

	public String getInsLinha05() {
		return insLinha05;
	}

	public void setInsLinha05(String insLinha05) {
		this.insLinha05 = insLinha05;
	}

	public String getInsLinha06() {
		return insLinha06;
	}

	public void setInsLinha06(String insLinha06) {
		this.insLinha06 = insLinha06;
	}

	public String getInsLinha07() {
		return insLinha07;
	}

	public void setInsLinha07(String insLinha07) {
		this.insLinha07 = insLinha07;
	}

	public String getInsLinha08() {
		return insLinha08;
	}

	public void setInsLinha08(String insLinha08) {
		this.insLinha08 = insLinha08;
	}

	public List<CobrancaItem> getItens() {
		return itens;
	}

	public void setItens(List<CobrancaItem> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCobranca == null) ? 0 : idCobranca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cobranca))
			return false;
		Cobranca other = (Cobranca) obj;
		if (idCobranca == null) {
			if (other.idCobranca != null)
				return false;
		} else if (!idCobranca.equals(other.idCobranca))
			return false;
		return true;
	}

}
