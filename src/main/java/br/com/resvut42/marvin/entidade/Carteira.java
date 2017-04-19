package br.com.resvut42.marvin.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/****************************************************************************
 * Entidade Carteira para geração de Boletos Desenvolvido por :
 * 
 * @author Bob-Odin - 12/04/2017
 ****************************************************************************/
@Entity
public class Carteira implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCarteira;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@Column(length = 50)
	private String descricao;

	@Column(length = 2)
	private String codCarteira;

	@Column(length = 10)
	private String agencia;

	@Column(length = 15)
	private String conta;

	@Column(length = 7)
	private String codMestre;

	public Long getIdCarteira() {
		return idCarteira;
	}

	public void setIdCarteira(Long idCarteira) {
		this.idCarteira = idCarteira;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodCarteira() {
		return codCarteira;
	}

	public void setCodCarteira(String codCarteira) {
		this.codCarteira = codCarteira;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCodMestre() {
		return codMestre;
	}

	public void setCodMestre(String codMestre) {
		this.codMestre = codMestre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCarteira == null) ? 0 : idCarteira.hashCode());
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
		Carteira other = (Carteira) obj;
		if (idCarteira == null) {
			if (other.idCarteira != null)
				return false;
		} else if (!idCarteira.equals(other.idCarteira))
			return false;
		return true;
	}

}
