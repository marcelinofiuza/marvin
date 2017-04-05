package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;


import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.ClienteContatos;
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
	
	private final long newItem = 90000;
	private long nextItem = newItem;

	private Set<ClienteContatos> listaClienteContatos;
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
		setListaClienteContatos(new HashSet<ClienteContatos>());
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
			
			clienteEdicao.getContatos().clear();
			clienteEdicao.getContatos().addAll(listaClienteContatos);
			serCliente.salvar(clienteEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
			e.printStackTrace();
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
	
	public void editCadastro() {
		preparaTela();
		clienteEdicao = clienteSelect;
		listaClienteContatos.clear();
		listaClienteContatos.addAll(clienteEdicao.getContatos());
	}
	
	public void addContato() {
		nextItem++;
		ClienteContatos clienteContatos = new ClienteContatos();
		clienteContatos.setCliente(clienteEdicao);
		clienteContatos.setIdContato(nextItem);
		listaClienteContatos.add(clienteContatos);
	}

	
	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void buscar(){
						
		List<Cliente> listaClientes = serCliente.listarTodos();
		if(!listaClientes.isEmpty()){
			clienteSelect = listaClientes.get(0);
		}else{
			clienteSelect = new Cliente();
		}
		
		RequestContext.getCurrentInstance().execute("PF('wgDados').show();");		
		
	}
	
	
	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(ClienteContatos contato) {
		listaClienteContatos.remove(contato);
	}
	
	public void novoCadastro() {
		preparaTela();
		clienteSelect = new Cliente();
		
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

	public Set<ClienteContatos> getListaClienteContatos() {
		return listaClienteContatos;
	}

	public void setListaClienteContatos(Set<ClienteContatos> listaClienteContatos) {
		this.listaClienteContatos = listaClienteContatos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
}
