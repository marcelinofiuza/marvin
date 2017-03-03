package br.com.resvut42.marvin.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/****************************************************************************
 * Entidade Contatos Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017 /
 ****************************************************************************/
@Entity
public class ContatosBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long idContato;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@Embedded
	private Contato contato = new Contato();

	public Long getIdContato() {
		return idContato;
	}

	public void setIdContato(Long idContato) {
		this.idContato = idContato;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idContato == null) ? 0 : idContato.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ContatosBanco))
			return false;
		ContatosBanco other = (ContatosBanco) obj;
		if (idContato == null) {
			if (other.idContato != null)
				return false;
		} else if (!idContato.equals(other.idContato))
			return false;
		return true;
	}

}
