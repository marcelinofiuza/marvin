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

import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.ClienteContatos;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.servico.SerCliente;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Cliente
 * 
 * @author: Thayro Rodrigues - 03/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleCliente implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/

	private static final long serialVersionUID = 1L;

	private Cliente clienteEdicao = new Cliente();
	private Cliente clienteSelect;

	private Conta conta;

	private final long newItem = 90000;
	private long nextItem = newItem;

	private List<ClienteContatos> listaClienteContatos;
	private List<Cliente> listaClientes = new ArrayList<Cliente>();

	@Autowired
	SerCliente serCliente;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {
		setListaClienteContatos(new ArrayList<ClienteContatos>());
		conta = new Conta();
	}

	/****************************************************************************
	 * Metodo Salvar
	 ****************************************************************************/
	public void salvar() {

		try {
			for (ClienteContatos contatosCliente : listaClienteContatos) {
				if (contatosCliente.getIdContato() > newItem) {
					contatosCliente.setIdContato(null);
				}
			}
			clienteEdicao.setConta(conta);
			clienteEdicao.setContatos(null);
			clienteEdicao.setContatos(listaClienteContatos);
			serCliente.salvar(clienteEdicao);
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
			serCliente.excluir(clienteSelect);
			clienteSelect = null;
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
		clienteEdicao = clienteSelect;
		conta = clienteEdicao.getConta();
		listaClienteContatos.clear();
		listaClienteContatos.addAll(clienteEdicao.getContatos());
	}

	/****************************************************************************
	 * Metodo adicionar novo contato
	 ****************************************************************************/
	public void addContato() {
		nextItem++;
		ClienteContatos clienteContatos = new ClienteContatos();
		clienteContatos.setCliente(clienteEdicao);
		clienteContatos.setIdContato(nextItem);
		listaClienteContatos.add(clienteContatos);
	}

//	/****************************************************************************
//	 * Buscar lista dos dados no banco
//	 ****************************************************************************/
//	public void buscar() {
//
//		List<Cliente> listaClientes = serCliente.listarTodos();
//		if (!listaClientes.isEmpty()) {
//			clienteSelect = listaClientes.get(0);
//		} else {
//			clienteSelect = new Cliente();
//		}
//
//		RequestContext.getCurrentInstance().execute("PF('wgDados').show();");
//
//	}

	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(ClienteContatos contato) {
		listaClienteContatos.remove(contato);
	}

	/****************************************************************************
	 * Prepara tela para novo cadastro
	 ****************************************************************************/	
	public void novoCadastro() {
		preparaTela();
		clienteEdicao = new Cliente();
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		preparaTela();
		clienteSelect = null;
		listaClientes = serCliente.listarTodos();
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

	public Cliente getClienteEdicao() {
		return clienteEdicao;
	}

	public void setClienteEdicao(Cliente clienteEdicao) {
		this.clienteEdicao = clienteEdicao;
	}

	public Cliente getClienteSelect() {
		return clienteSelect;
	}

	public void setClienteSelect(Cliente clienteSelect) {
		this.clienteSelect = clienteSelect;
	}

	public List<ClienteContatos> getListaClienteContatos() {
		return listaClienteContatos;
	}

	public void setListaClienteContatos(List<ClienteContatos> listaClienteContatos) {
		this.listaClienteContatos = listaClienteContatos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
