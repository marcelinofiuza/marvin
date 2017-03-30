package br.com.resvut42.marvin.entidade;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;


@Entity
public class Cliente2 {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idCliente ;
	
	@NotEmpty
	@Column(length = 80, nullable = false)
	private String razaoSocial;

	@NotEmpty
	@Column(length = 40, nullable = false)
	private String fantasia;
	
	@CNPJ
	@Column(length = 18, nullable = true)
	@Nullable
	private String cnpj;

	@CPF
	@Column(length = 14, nullable = true)
	@Nullable
	private String cpf;
	
	@Column(length = 15)
	private String insEstadual;

	@Column(length = 15)
	private String insMunicipal;
	
	@Column(length = 15)
	private String unidade;
	
	@Column(length = 2)
	private int diaPagamento;
	
	private double valor ;
	
	private float fracao ;
	
	private float fracao2 ;
	
	private float fracao3 ;
	
	@Embedded
	private Endereco endereco = new Endereco();
	
	@Embedded
	private Contato contatos = new Contato();
	
	@Embedded
	private Conta conta = new Conta();
	
	
	public Cliente2() {
	
	}
	
	public Cliente2(long idCliente, String razaoSocial, String fantasia, String cnpj, String cpf, String insEstadual,
			String insMunicipal, String unidade, int diaPagamento, double valor, float fracao, float fracao2,
			float fracao3, Endereco endereco, Contato contatos, Conta conta) {
		super();
		this.idCliente = idCliente;
		this.razaoSocial = razaoSocial;
		this.fantasia = fantasia;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.insEstadual = insEstadual;
		this.insMunicipal = insMunicipal;
		this.unidade = unidade;
		this.diaPagamento = diaPagamento;
		this.valor = valor;
		this.fracao = fracao;
		this.fracao2 = fracao2;
		this.fracao3 = fracao3;
		this.endereco = endereco;
		this.contatos = contatos;
		this.conta = conta;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getInsEstadual() {
		return insEstadual;
	}

	public void setInsEstadual(String insEstadual) {
		this.insEstadual = insEstadual;
	}

	public String getInsMunicipal() {
		return insMunicipal;
	}

	public void setInsMunicipal(String insMunicipal) {
		this.insMunicipal = insMunicipal;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(int diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public float getFracao() {
		return fracao;
	}

	public void setFracao(float fracao) {
		this.fracao = fracao;
	}

	public float getFracao2() {
		return fracao2;
	}

	public void setFracao2(float fracao2) {
		this.fracao2 = fracao2;
	}

	public float getFracao3() {
		return fracao3;
	}

	public void setFracao3(float fracao3) {
		this.fracao3 = fracao3;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contatos;
	}

	public void setContato(Contato contatos) {
		this.contatos = contatos;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCliente ^ (idCliente >>> 32));
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
		Cliente2 other = (Cliente2) obj;
		if (idCliente != other.idCliente)
			return false;
		return true;
	}


	


}
