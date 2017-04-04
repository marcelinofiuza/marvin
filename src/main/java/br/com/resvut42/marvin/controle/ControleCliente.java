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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
			
			serCliente.salvar(clienteSelect);
			mensagens.info("Registro salvo com sucesso!");
			
			//Atualiza memória
			atualizaSessao();
			
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}		
		
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm"));	
		
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
	 * Atualiza a sessão com os novos dados do Cliente
	 ****************************************************************************/	
	private void atualizaSessao(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSessao = attr.getRequest().getSession(true); // true == allow create	
		httpSessao.setAttribute("EMPRESA", clienteSelect);
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
