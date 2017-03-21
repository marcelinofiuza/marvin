package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

import org.hibernate.validator.constraints.NotEmpty;

import br.com.resvut42.marvin.enums.Febraban;

/****************************************************************************
 * Entidade Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017 /
 ****************************************************************************/
@Entity
public class Banco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBanco;

	@Enumerated(EnumType.STRING)
	@Column(length = 5)
	private Febraban febraban;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	@NotEmpty
	@Column(length = 10)
	private String agencia;

	@NotEmpty
	@Column(length = 30)
	private String nomeAgencia;

	@NotEmpty
	@Column(length = 15)
	private String numeroConta;

	@Embedded
	private Endereco endereco = new Endereco();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco")
	private List<BancoContatos> contatos = new ArrayList<BancoContatos>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco")
	private List<BancoPeriodo> periodos = new ArrayList<BancoPeriodo>();

	public Long getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Long idBanco) {
		this.idBanco = idBanco;
	}

	public Febraban getFebraban() {
		return febraban;
	}

	public void setFebraban(Febraban febraban) {
		this.febraban = febraban;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<BancoContatos> getContatos() {
		return contatos;
	}

	public void setContatos(List<BancoContatos> contatos) {
		this.contatos = contatos;
	}

	public void addContato(BancoContatos contato) {
		contato.setBanco(this);
		this.contatos.add(contato);
	}

	public List<BancoPeriodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodo(List<BancoPeriodo> periodos) {
		this.periodos = periodos;
	}

	public void addSaldo(BancoPeriodo periodo) {
		periodo.setBanco(this);
		this.periodos.add(periodo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBanco == null) ? 0 : idBanco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Banco))
			return false;
		Banco other = (Banco) obj;
		if (idBanco == null) {
			if (other.idBanco != null)
				return false;
		} else if (!idBanco.equals(other.idBanco))
			return false;
		return true;
	}

}
