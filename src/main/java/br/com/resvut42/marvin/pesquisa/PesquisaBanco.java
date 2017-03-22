package br.com.resvut42.marvin.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.servico.SerBanco;

/****************************************************************************
 * Classe controle para View de Pesquisa de Bancos
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaBanco extends AbstrataPesquisa implements Serializable{

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Banco> listaBancos;

	@Autowired
	SerBanco serBanco;
	
	/****************************************************************************
	 * Buscar lista de empresas
	 ****************************************************************************/
	@PostConstruct
	public void listarBanco() {
		listaBancos = new ArrayList<>();
		listaBancos = serBanco.listarTodos();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Banco> getListaBancos() {
		return listaBancos;
	}

}
