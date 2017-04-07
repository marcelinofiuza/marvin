package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.entidade.CobrancaItem;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.servico.SerCobranca;
import br.com.resvut42.marvin.util.FacesMessages;

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
	private List<CobrancaItem> listaCobrancaItem = new ArrayList<CobrancaItem>();
	private Cobranca cobrancaEdicao = new Cobranca();
	private Cobranca cobrancaSelect;
	private Conta conta = new Conta();

	private final long newItem = 90000;
	private long nextItem = newItem;
	private long editItem;

	@Autowired
	SerCobranca serCobranca;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Salvar o registro
	 ****************************************************************************/
	public void salvar() {
		try {			
			validaItens();
			for (CobrancaItem item : listaCobrancaItem) {
				if (item.getIdItem() > newItem) {
					item.setIdItem(null);
				}
			}
			cobrancaEdicao.getItens().clear();
			cobrancaEdicao.getItens().addAll(listaCobrancaItem);
			cobrancaEdicao.setConta(conta);
			serCobranca.salvar(cobrancaEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			
		} catch (Exception e) {
			// TODO: handle exception
			//se houve erro, adiciona numero do item novamente
			nextItem = newItem;
			for (CobrancaItem item : listaCobrancaItem) {				
				if (item.getIdItem() == null) {
					nextItem++;
					item.setIdItem(nextItem);					
				}
			}						
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Excluir dados
	 ****************************************************************************/
	public void excluir() {
		try {
			serCobranca.excluir(cobrancaSelect);
			cobrancaSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Buscar lista dos dados de cobrança
	 ****************************************************************************/
	public void listar() {
		listaCobranca = serCobranca.listarTodos();
	}

	/****************************************************************************
	 * Prepara tela para novo cadastro de cobranca
	 ****************************************************************************/
	public void novoCadastro() {
		cobrancaEdicao = new Cobranca();
		conta = new Conta();
		listaCobrancaItem = new ArrayList<CobrancaItem>();
	}

	/****************************************************************************
	 * Metodo Editar Cobrança
	 ****************************************************************************/
	public void editCadastro() {
		cobrancaEdicao = cobrancaSelect;
		conta = cobrancaEdicao.getConta();
		listaCobrancaItem.clear();
		listaCobrancaItem.addAll(cobrancaEdicao.getItens());
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = new Conta();
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Metodo adicionar novo item de cobrança
	 ****************************************************************************/
	public void addItem() {
		nextItem++;
		CobrancaItem cobrancaItem = new CobrancaItem();
		cobrancaItem.setCobranca(cobrancaEdicao);
		cobrancaItem.setIdItem(nextItem);
		listaCobrancaItem.add(cobrancaItem);
	}

	/****************************************************************************
	 * Seta o item que recebeu a acao
	 ****************************************************************************/
	public void itemAcao(Object item) {
		editItem = (long) item;
	}

	/****************************************************************************
	 * Metodo remover item de cobrança
	 ****************************************************************************/
	public void removeItem(CobrancaItem item) {
		listaCobrancaItem.remove(item);
	}

	/****************************************************************************
	 * Valida os itens informados
	 ****************************************************************************/
	public void validaItens() {
		for (CobrancaItem item : listaCobrancaItem) {
			
			if(item.getDiaVencimento() == null){
				item.setDiaVencimento(0);
			}			
			if(item.getValor() == null){
				item.setValor(new BigDecimal(0));
			}
			if(item.getFracao1() == null){
				item.setFracao1(new BigDecimal(0));
			}
			if(item.getFracao2() == null){
				item.setFracao2(new BigDecimal(0));
			}
			if(item.getFracao3() == null){
				item.setFracao3(new BigDecimal(0));
			}			
		}

	}
	
	/****************************************************************************
	 * Resgata o cliente selecionado no dialogo
	 ****************************************************************************/
	public void clienteSelecionado(SelectEvent event) {
		Cliente cliente = (Cliente) event.getObject();
		for (CobrancaItem item : listaCobrancaItem) {
			if (item.getIdItem() == editItem) {
				item.setCliente(cliente);
			}
		}
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

	public List<CobrancaItem> getListaCobrancaItem() {
		return listaCobrancaItem;
	}

	public void setListaCobrancaItem(List<CobrancaItem> listaCobrancaItem) {
		this.listaCobrancaItem = listaCobrancaItem;
	}

	public Cobranca getCobrancaEdicao() {
		return cobrancaEdicao;
	}

	public void setCobrancaEdicao(Cobranca cobrancaEdicao) {
		this.cobrancaEdicao = cobrancaEdicao;
	}

	public Cobranca getCobrancaSelect() {
		return cobrancaSelect;
	}

	public void setCobrancaSelect(Cobranca cobrancaSelect) {
		this.cobrancaSelect = cobrancaSelect;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
