package br.com.resvut42.marvin.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/****************************************************************************
 * Entidade Contatos Cliente Desenvolvido por :
 * 
 * @author Gustavo - 15/03/2017
 ****************************************************************************/
@Entity
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idFornecedor;

	@Column(length = 15)
	private String frCodigo;

	@NotEmpty
	@Column(length = 80, nullable = false)
	private String razaoSocial;

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

	@Embedded
	private Endereco endereco = new Endereco();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idFornecedor")
	private List<FornecedorContatos> contatos = new ArrayList<FornecedorContatos>();

	public Long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getFrCodigo() {
		return frCodigo;
	}

	public void setFrCodigo(String frCodigo) {
		this.frCodigo = frCodigo;
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

	public List<FornecedorContatos> getContatos() {
		return contatos;
	}

	public void setContatos(List<FornecedorContatos> contatos) {
		this.contatos = contatos;
	}

	public void addContato(FornecedorContatos contato) {
		contato.setFornecedor(this);
		this.contatos.add(contato);
	}
	
	public void addContato(Contato contato) {
		FornecedorContatos fc = new FornecedorContatos();
		fc.setIdContato(null);
		fc.setContato(contato);
		addContato(fc);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFornecedor == null) ? 0 : idFornecedor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Fornecedor))
			return false;
		Fornecedor other = (Fornecedor) obj;
		if (idFornecedor == null) {
			if (other.idFornecedor != null)
				return false;
		} else if (!idFornecedor.equals(other.idFornecedor))
			return false;
		return true;
	}

}
