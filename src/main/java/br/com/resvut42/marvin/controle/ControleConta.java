package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.enums.AnaliticaSintetica;
import br.com.resvut42.marvin.servico.SerConta;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Plano de contas
 * 
 * @author: Bob-Odin - 31/01/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleConta implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private TreeNode treeContas;
	private TreeNode contaSelect;
	private Conta contaEdicao = new Conta();

	@Autowired
	SerConta serConta;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Metodo Salvar
	 ****************************************************************************/
	public void salvar() {
		try {
			serConta.salvar(contaEdicao);
			listar();
			contaSelect = null;
			contaEdicao = new Conta();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela", "frm:toolbar"));
	}

	/****************************************************************************
	 * Metodo Excluir
	 ****************************************************************************/
	public void excluir() {
		try {
			Conta contatmp = (Conta) contaSelect.getData();
			serConta.excluir(contatmp);
			listar();
			contaSelect = null;
			contaEdicao = new Conta();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela", "frm:toolbar"));
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		treeContas = serConta.listarTodos();
	}

	/****************************************************************************
	 * Preparar objetos para novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {

		Conta contatmp = (Conta) contaSelect.getData();

		if (contatmp.getTipoConta().equals(AnaliticaSintetica.ANALITICA)) {
			contatmp = (Conta) contaSelect.getData();
			contatmp = contatmp.getContaPai();
		}

		contaEdicao = new Conta();
		contaEdicao.setContaPai(contatmp);
		contaEdicao.setNatureza(contatmp.getNatureza());
		contaEdicao.setStatus(contatmp.getStatus());

	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editCadastro() {
		contaEdicao = (Conta) contaSelect.getData();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public TreeNode getTreeContas() {
		return treeContas;
	}

	public Conta getContaEdicao() {
		return contaEdicao;
	}

	public void setContaEdicao(Conta contaEdicao) {
		this.contaEdicao = contaEdicao;
	}

	public TreeNode getContaSelect() {
		return contaSelect;
	}

	public void setContaSelect(TreeNode contaSelect) {
		this.contaSelect = contaSelect;
	}

}
