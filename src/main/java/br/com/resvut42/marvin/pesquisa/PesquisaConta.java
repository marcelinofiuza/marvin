package br.com.resvut42.marvin.pesquisa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.servico.SerConta;

/****************************************************************************
 * Classe controle para View de Pesquisa de Conta Contábil
 * 
 * @author: Bob-Odin - 11/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaConta implements Serializable {

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
	 * Retorna o registro selecionado na lista
	 ****************************************************************************/	
	public void selecionar(Conta conta){
		RequestContext.getCurrentInstance().closeDialog(conta);
	}
	
	/****************************************************************************
	 * Abre o xhtml em forma de dialogo
	 ****************************************************************************/	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", "800");		
		opcoes.put("contentHeight", "450");
		opcoes.put("width", "800");
		opcoes.put("height", "450");

		RequestContext.getCurrentInstance().openDialog("resources/ajudapesquisa/pesquisaConta", opcoes, null);
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public TreeNode getTreeContas() {
		return treeContas;
	}

}
