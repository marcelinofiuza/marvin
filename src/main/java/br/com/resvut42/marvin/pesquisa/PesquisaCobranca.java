package br.com.resvut42.marvin.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.servico.SerCobranca;

/****************************************************************************
 * Classe controle para View de Pesquisa de Cobrança
 * 
 * @author: Bob-Odin - 11/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaCobranca extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Cobranca> listaCobranca;

	@Autowired
	SerCobranca serCobranca;
	
	/****************************************************************************
	 * Buscar lista de empresas
	 ****************************************************************************/
	@PostConstruct
	public void listarCobranca() {		
		listaCobranca = serCobranca.listarTodos();
	}	
		
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	
	public List<Cobranca> getListaCobranca() {
		return listaCobranca;
	}

}
