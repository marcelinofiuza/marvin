package br.com.resvut42.marvin.pesquisa;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.servico.SerConta;

/****************************************************************************
 * Classe controle para View de Pesquisa de Conta Contábil
 * 
 * @author: Bob-Odin - 11/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaConta extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private TreeNode treeContas;

	@Autowired
	SerConta serConta;

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	@PostConstruct
	public void listar() {
		treeContas = serConta.listarTodos();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public TreeNode getTreeContas() {
		return treeContas;
	}

}
