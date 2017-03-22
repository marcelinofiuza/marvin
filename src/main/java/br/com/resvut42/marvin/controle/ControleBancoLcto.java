package br.com.resvut42.marvin.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.servico.SerBanco;

/****************************************************************************
 * Classe controle para View da Tela do Lançamento Bancário
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleBancoLcto implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private Banco banco;
	private BancoPeriodo bancoPeriodo;

	@Autowired
	SerBanco serBanco;
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').show();");
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = new Banco();
		banco = (Banco) event.getObject();
		banco.setPeriodo(serBanco.ordenaPeriodo(banco.getPeriodos(), "D"));
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public BancoPeriodo getBancoPeriodo() {
		return bancoPeriodo;
	}

	public void setBancoPeriodo(BancoPeriodo bancoPeriodo) {
		this.bancoPeriodo = bancoPeriodo;
	}

}
