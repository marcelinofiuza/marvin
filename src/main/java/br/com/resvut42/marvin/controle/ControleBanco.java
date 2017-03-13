package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.entidade.ContatosBanco;
import br.com.resvut42.marvin.enums.Estado;
import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Banco
 * 
 * @author: Bob-Odin - 02/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleBanco implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Banco> listaBancos = new ArrayList<Banco>();
	private Banco bancoEdicao = new Banco();
	private Banco bancoSelect;
	private Conta conta;
	
	private final long newItem = 90000;
	private ContatosBanco contatosBanco = new ContatosBanco();	
	private Set<ContatosBanco> listaContatosBanco;	
	private long idItmContato;

	@Autowired
	SerBanco serBanco;
	@Autowired
	private FacesMessages mensagens;
	
	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/	
	@PostConstruct
	public void preparaTela(){
		idItmContato = newItem;
		listaContatosBanco = new HashSet<ContatosBanco>();
		limpaContato();		
	}	
	
	/****************************************************************************
	 * Limpa contato
	 ****************************************************************************/
	public void limpaContato() {
		contatosBanco = new ContatosBanco();
	}
	
	/****************************************************************************
	 * Salvar os dados no banco
	 ****************************************************************************/
	public void salvar() {
		try {			
			for (ContatosBanco contatosBanco : listaContatosBanco) {
				if(contatosBanco.getIdContato() > newItem){
					contatosBanco.setIdContato(null);
				}
			}			
			bancoEdicao.setConta(conta);
			bancoEdicao.getContatos().clear();
			bancoEdicao.getContatos().addAll(listaContatosBanco);
			serBanco.salvar(bancoEdicao);			
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
			serBanco.excluir(bancoSelect);
			bancoSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editCadastro() {		
		preparaTela();
		bancoEdicao = bancoSelect;
		conta = bancoEdicao.getConta();
		listaContatosBanco.clear();
		listaContatosBanco.addAll(bancoEdicao.getContatos());
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		preparaTela();			
		bancoSelect = null;
		listaBancos = serBanco.listarTodos();		
	}

	/****************************************************************************
	 * Preparar objetos para novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {
		preparaTela();
		bancoEdicao = new Banco();
	}

	/****************************************************************************
	 * Adiconar o contato a lista de contatos
	 ****************************************************************************/
	public void addContato() {	
		if(contatosBanco.getContato().getNomeContato().isEmpty()){
			mensagens.error("Nome do contato é obrigatório!");		
		}else{						
			if(contatosBanco.getIdContato() == null){
				idItmContato ++;
				contatosBanco.setIdContato(idItmContato);			
			}
			contatosBanco.setBanco(bancoEdicao);	
			listaContatosBanco.add(contatosBanco);
			limpaContato();
		}
	}

	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(ContatosBanco contato) {
		listaContatosBanco.remove(contato);
		limpaContato();
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = new Conta();
		conta = (Conta) event.getObject();	
	}	
	
	/****************************************************************************
	 * -- Lista de opções de enums
	 ****************************************************************************/

	public Febraban[] getFebraban() {
		return Febraban.values();
	}

	public Estado[] getEstados() {
		return Estado.values();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Banco> getListaBancos() {
		return listaBancos;
	}

	public Banco getBancoEdicao() {
		return bancoEdicao;
	}

	public void setBancoEdicao(Banco bancoEdicao) {
		this.bancoEdicao = bancoEdicao;
	}

	public Banco getBancoSelect() {
		return bancoSelect;
	}

	public void setBancoSelect(Banco bancoSelect) {
		this.bancoSelect = bancoSelect;
	}

	public ContatosBanco getContatosBanco() {		
		if(contatosBanco == null){
			contatosBanco = new ContatosBanco();
		}		
		return contatosBanco;
	}

	public void setContatosBanco(ContatosBanco contatosBanco) {
		this.contatosBanco = contatosBanco;
	}

	public Set<ContatosBanco> getListaContatosBanco() {
		return listaContatosBanco;
	}

	public void setListaContatosBanco(Set<ContatosBanco> listaContatosBanco) {
		this.listaContatosBanco = listaContatosBanco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
