package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.BoletoItem;
import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.enums.StatusBoleto;
import br.com.resvut42.marvin.estrutura.CalculoBoleto;
import br.com.resvut42.marvin.servico.SerBoleto;
import br.com.resvut42.marvin.util.FacesMessages;
import br.com.resvut42.marvin.util.R42Data;

/****************************************************************************
 * Classe controle para View da Tela do Banco
 * 
 * @author: Bob-Odin - 08/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleBoleto implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<Boleto> listaBoletos = new ArrayList<Boleto>();
	private List<BoletoItem> listaBoletoItem = new ArrayList<BoletoItem>();
	private Boleto boletoEdicao = new Boleto();
	private Boleto boletoSelect;
	private Banco banco;
	private Conta conta;
	private CalculoBoleto calculoBoleto;

	private final long newItem = 9000000;
	private long nextItem = newItem;
	private long editItem;

	@Autowired
	SerBoleto serBoleto;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').show();");
	}

	/****************************************************************************
	 * Salvar o registro
	 ****************************************************************************/
	public void salvar() {
		try {
			validaItens();
			for (BoletoItem item : listaBoletoItem) {
				if (item.getIdItem() > newItem) {
					item.setIdItem(null);
				}
			}
			boletoEdicao.setConta(conta);
			boletoEdicao.setBanco(banco);
			boletoEdicao.setItens(listaBoletoItem);
			serBoleto.salvar(boletoEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
		} catch (Exception e) {
			// TODO: handle exception
			// se houve erro, adiciona numero do item novamente
			nextItem = newItem;
			for (BoletoItem item : listaBoletoItem) {
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
	 * Lista os boletos de acordo com o banco
	 ****************************************************************************/
	public void listar() {
		listaBoletos = serBoleto.listarPorBanco(banco);
	}

	/****************************************************************************
	 * Prepara tela para geração de novos Boletos
	 ****************************************************************************/
	public void novoBoleto() {
		boletoEdicao = new Boleto();
		listaBoletoItem = new ArrayList<>();
		conta = new Conta();
		boletoEdicao.setStatusBoleto(StatusBoleto.ABERTO);
		boletoEdicao.setLancamento(R42Data.dataAtual());
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = (Banco) event.getObject();
	}

	/****************************************************************************
	 * Efetua a confirmação do banco e periodo selecionado
	 ****************************************************************************/
	public void confirmaBanco() {
		listar();
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').hide();");
	}

	/****************************************************************************
	 * Resgata a Conta selecionado no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Metodo adicionar novo item de cobrança
	 ****************************************************************************/
	public void addItem() {
		nextItem++;
		BoletoItem boletoItem = new BoletoItem();
		boletoItem.setBoleto(boletoEdicao);
		boletoItem.setIdItem(nextItem);
		listaBoletoItem.add(boletoItem);
	}

	/****************************************************************************
	 * Metodo remover item de boleto
	 ****************************************************************************/
	public void removeItem(BoletoItem item) {
		listaBoletoItem.remove(item);
	}

	/****************************************************************************
	 * Seta o item que recebeu a acao
	 ****************************************************************************/
	public void itemAcao(Object item) {
		editItem = (long) item;
	}

	/****************************************************************************
	 * Resgata o cliente selecionado no dialogo
	 ****************************************************************************/
	public void clienteSelecionado(SelectEvent event) {
		Cliente cliente = (Cliente) event.getObject();
		for (BoletoItem item : listaBoletoItem) {
			if (item.getIdItem() == editItem) {
				item.setCliente(cliente);
			}
		}
	}

	/****************************************************************************
	 * Valida os itens informados
	 ****************************************************************************/
	public void validaItens() {
		for (BoletoItem item : listaBoletoItem) {
			if (item.getValor0() == null) {
				item.setValor0(new BigDecimal(0));
			}
			if (item.getValor1() == null) {
				item.setValor1(new BigDecimal(0));
			}
			if (item.getValor2() == null) {
				item.setValor2(new BigDecimal(0));
			}
			if (item.getValor3() == null) {
				item.setValor3(new BigDecimal(0));
			}
		}

	}

	/****************************************************************************
	 * Metodo Editar Boleto
	 ****************************************************************************/
	public void editCadastro() {
		boletoEdicao = boletoSelect;
		conta = boletoEdicao.getConta();
		listaBoletoItem.clear();
		listaBoletoItem.addAll(boletoEdicao.getItens());
	}

	/****************************************************************************
	 * Excluir dados
	 ****************************************************************************/
	public void excluir() {
		try {
			serBoleto.excluir(boletoSelect);
			boletoSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata a Cobrança selecionada no dialogo
	 ****************************************************************************/
	public void cobrancaSelecionada(SelectEvent event) {
		calculoBoleto.setCobranca((Cobranca) event.getObject());
	}

	/****************************************************************************
	 * Abrir dialogo para seleção de cobrança
	 ****************************************************************************/	
	public void abrirDialogoCobranca(){
		calculoBoleto = new CalculoBoleto();
	}

	/****************************************************************************
	 * Confirmar cobrança selecionada e calcular os dados
	 ****************************************************************************/	
	public void confirmaCobranca(){
		boletoEdicao = calculoBoleto.getBoleto();
		listaBoletoItem = boletoEdicao.getItens();
		conta = boletoEdicao.getConta();
	}	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Boleto> getListaBoletos() {
		return listaBoletos;
	}

	public Boleto getBoletoEdicao() {
		return boletoEdicao;
	}

	public void setBoletoEdicao(Boleto boletoEdicao) {
		this.boletoEdicao = boletoEdicao;
	}

	public Boleto getBoletoSelect() {
		return boletoSelect;
	}

	public void setBoletoSelect(Boleto boletoSelect) {
		this.boletoSelect = boletoSelect;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public CalculoBoleto getCalculoBoleto() {
		return calculoBoleto;
	}

	public void setCalculoBoleto(CalculoBoleto calculoBoleto) {
		this.calculoBoleto = calculoBoleto;
	}

	public List<BoletoItem> getListaBoletoItem() {
		return listaBoletoItem;
	}

	public void setListaBoletoItem(List<BoletoItem> listaBoletoItem) {
		this.listaBoletoItem = listaBoletoItem;
	}

}
