package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.servico.SerCobranca;

/****************************************************************************
 * Classe controle para View da Tela de Cobranca
 * 
 * @author: Bob-Odin - 04/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleCobranca implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Cobranca> listaCobranca = new ArrayList<Cobranca>();

	@Autowired
	SerCobranca serCobranca;

	/****************************************************************************
	 * Buscar lista dos dados de cobrança
	 ****************************************************************************/
	public void listar() {
		listaCobranca = serCobranca.listarTodos();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Cobranca> getListaCobranca() {
		return listaCobranca;
	}

	public void setListaCobranca(List<Cobranca> listaCobranca) {
		this.listaCobranca = listaCobranca;
	}

}
