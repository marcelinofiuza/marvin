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
import javax.persistence.OrderBy;

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

	@Column(length = 10)
	private String idMigracao;

	@Enumerated(EnumType.STRING)
	@Column(length = 5)
	private Febraban febraban;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	@Column(length = 10)
	private String agencia;

	@Column(length = 30)
	private String nomeAgencia;

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
	@OrderBy("dataInicio")
	private List<BancoPeriodo> periodos = new ArrayList<BancoPeriodo>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco")
	private List<Carteira> carteiras = new ArrayList<Carteira>();

	private Long seqArquivo;	
	
	public Long getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Long idBanco) {
		this.idBanco = idBanco;
	}

	public String getIdMigracao() {
		return idMigracao;
	}

	public void setIdMigracao(String idMigracao) {
		this.idMigracao = idMigracao;
	}

	public Febraban getFebraban() {
		return febraban;
	}

	public void setFebraban(Febraban febraban) {
		this.febraban = febraban;
	}

	public String getIdeDescricao(){
		return idBanco+" - "+descricao;
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

	public void addContato(Contato contato) {
		BancoContatos bc = new BancoContatos();
		bc.setIdContato(null);
		bc.setContato(contato);
		addContato(bc);
	}

	public List<BancoPeriodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<BancoPeriodo> periodos) {
		this.periodos = periodos;
	}

	public BancoPeriodo getPeriodo(Long idPeriodo) {
		for (BancoPeriodo bancoPeriodo : periodos) {
			if (bancoPeriodo.getIdPeriodo() == idPeriodo) {
				return bancoPeriodo;
			}
		}
		return null;
	}

	public void addSaldo(BancoPeriodo periodo) {
		periodo.setBanco(this);
		this.periodos.add(periodo);
	}

	public List<Carteira> getCarteiras() {
		return carteiras;
	}

	public void setCarteiras(List<Carteira> carteiras) {
		this.carteiras = carteiras;
	}

	public void addCarteira(Carteira carteira) {
		carteira.setBanco(this);
		this.carteiras.add(carteira);
	}
	
	public Long getSeqArquivo() {
		return seqArquivo;
	}

	public void setSeqArquivo(Long seqArquivo) {
		this.seqArquivo = seqArquivo;
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
