package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Fornecedor;
import br.com.resvut42.marvin.entidade.FornecedorContatos;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.servico.SerFornecedor;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Fornecedor
 * 
 * @author: Thayro Rodrigues - 12/04/2017
 ****************************************************************************/

@Named
@ViewScoped
public class ControleFornecedor implements Serializable {
	
	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	
	private static final long serialVersionUID = 1L;
	
	private Fornecedor fornecedorEdicao = new Fornecedor();
	private Fornecedor fornecedorSelect;

	private Conta conta;

	private final long newItem = 90000;
	private long nextItem = newItem;

	private List<FornecedorContatos> listaFornecedorContatos;
	private List<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();

	@Autowired
	SerFornecedor serFornecedor;
	@Autowired
	FacesMessages mensagens;
	
	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {
		setListaFornecedorContatos(new ArrayList<FornecedorContatos>());
		conta = new Conta();
	}
	
	/****************************************************************************
	 * Metodo Salvar
	 ****************************************************************************/
	public void salvar() {

		try {
			for (FornecedorContatos contatosFornecedor : listaFornecedorContatos) {
				if (contatosFornecedor.getIdContato() > newItem) {
					contatosFornecedor.setIdContato(null);
				}
			}
			fornecedorEdicao.setConta(null);
			fornecedorEdicao.setContatos(null);
			fornecedorEdicao.setContatos(listaFornecedorContatos);
			serFornecedor.salvar(fornecedorEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
	}
	
	/****************************************************************************
	 * Excluir dados
	 ****************************************************************************/
	public void excluir() {
		try {
			serFornecedor.excluir(fornecedorSelect);
			fornecedorSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}
	
	/****************************************************************************
	 * Metodo Editar Cliente
	 ****************************************************************************/
	public void editCadastro() {
		preparaTela();
		fornecedorEdicao = fornecedorSelect;
		conta = fornecedorEdicao.getConta();
		listaFornecedorContatos.clear();
		listaFornecedorContatos.addAll(fornecedorEdicao.getContatos());
	}
	
	/****************************************************************************
	 * Metodo adicionar novo contato
	 ****************************************************************************/
	public void addContato() {
		nextItem++;
		FornecedorContatos fornecedorContatos = new FornecedorContatos();
		fornecedorContatos.setFornecedor(fornecedorEdicao);
		fornecedorContatos.setIdContato(nextItem);
		listaFornecedorContatos.add(fornecedorContatos);
	}
	
	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(FornecedorContatos contato) {
		listaFornecedorContatos.remove(contato);
	}
	
	/****************************************************************************
	 * Prepara tela para novo cadastro
	 ****************************************************************************/	
	public void novoCadastro() {
		preparaTela();
		fornecedorEdicao = new Fornecedor();
	}
	
	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		preparaTela();
		fornecedorSelect = null;
		listaFornecedores = serFornecedor.listarTodos();
	}
	
	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = new Conta();
		conta = (Conta) event.getObject();
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	
	public Fornecedor getFornecedorEdicao() {
		return fornecedorEdicao;
	}
	public void setFornecedorEdicao(Fornecedor fornecedorEdicao) {
		this.fornecedorEdicao = fornecedorEdicao;
	}
	public Fornecedor getFornecedorSelect() {
		return fornecedorSelect;
	}
	public void setFornecedorSelect(Fornecedor fornecedorSelect) {
		this.fornecedorSelect = fornecedorSelect;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public long getNextItem() {
		return nextItem;
	}
	public void setNextItem(long nextItem) {
		this.nextItem = nextItem;
	}
	public List<FornecedorContatos> getListaFornecedorContatos() {
		return listaFornecedorContatos;
	}
	public void setListaFornecedorContatos(List<FornecedorContatos> listaFornecedorContatos) {
		this.listaFornecedorContatos = listaFornecedorContatos;
	}
	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}
	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

}
