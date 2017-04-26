package br.com.resvut42.marvin.controle;

import java.io.Serializable;
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
import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.enums.DebitoCredito;
import br.com.resvut42.marvin.enums.OrigemLcto;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.servico.SerBancoLcto;
import br.com.resvut42.marvin.util.FacesMessages;

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
	private Banco bancoCredito;
	private BancoPeriodo bancoPeriodo;
	private List<BancoPeriodo> listaPeriodo = new ArrayList<>();
	private BancoLcto bancoLcto = new BancoLcto();
	private BancoLcto lctoSelect;
	private Conta conta;
	private boolean displayCheque = false;

	@Autowired
	SerBanco serBanco;
	@Autowired
	SerBancoLcto serBancoLcto;
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
	 * Salvar o lançamento contábil
	 ****************************************************************************/
	public void salvar() {
		try {
			bancoLcto.setConta(conta);
			serBancoLcto.salvar(bancoLcto);
			resgataPeriodo();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editLcto() {
		bancoLcto = lctoSelect;
		conta = bancoLcto.getConta();
	}

	/****************************************************************************
	 * Excluir registro selecionado
	 ****************************************************************************/
	public void excluir() {
		try {
			if(lctoSelect.getOrigemLcto() == OrigemLcto.TRF ||
			   lctoSelect.getOrigemLcto() == OrigemLcto.BCO){
			
				serBancoLcto.excluir(lctoSelect);
				resgataPeriodo();
				mensagens.info("Registro excluido com sucesso!");
				
			} else {
				mensagens.error("Exclusão deve ser feito na origem do lançamento!");
			}
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata o periodo selecionado
	 ****************************************************************************/
	public void resgataPeriodo() {
		
		//atualiza dados do banco selecionado
		Long idPeriodo = bancoPeriodo.getIdPeriodo();
		banco = serBanco.buscarPorId(banco.getIdBanco());
		serBanco.montaSaldo(banco, idPeriodo);

		//Resgatar periodo atualizado
		bancoPeriodo = banco.getPeriodo(idPeriodo);
				
		lctoSelect = null;
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = (Banco) event.getObject();
		listaPeriodo = banco.getPeriodos();
	}

	/****************************************************************************
	 * Efetua a confirmação do banco e periodo selecionado
	 ****************************************************************************/
	public void confirmaBanco() {		
		if(bancoPeriodo != null){
			resgataPeriodo();
			RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').hide();");
		}else{
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Nenhum periodo para lançamento!");			
		}
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Preparar objetos para novo lancamento
	 ****************************************************************************/
	public void novoLancamento() {
		conta = new Conta();
		bancoLcto = new BancoLcto();
		bancoLcto.setOrigemLcto(OrigemLcto.BCO);
		bancoLcto.setBancoPeriodo(bancoPeriodo);
	}

	/****************************************************************************
	 * Evento quando é feita alteração no tipo de lançamento
	 ****************************************************************************/
	public void changeTipoLcto() {
		bancoLcto.setCheque(false);
		displayCheque = false;
		if (bancoLcto.getTipoLcto() == DebitoCredito.DEBITO) {
			displayCheque = true;
		}
	}

	/****************************************************************************
	 * Resgata o banco selecionada no dialogo
	 ****************************************************************************/
	public void bancoTransfSelecionada(SelectEvent event) {
		bancoCredito = (Banco) event.getObject();
	}

	/****************************************************************************
	 * Prepara objetos para nova transferencia
	 ****************************************************************************/
	public void novaTransferencia() {
		bancoCredito = new Banco();
		bancoLcto = new BancoLcto();
		bancoLcto.setBancoPeriodo(bancoPeriodo);
		bancoLcto.setOrigemLcto(OrigemLcto.TRF);
		bancoLcto.setTipoLcto(DebitoCredito.DEBITO);
	}

	/****************************************************************************
	 * Salva o registro de transferencia
	 ****************************************************************************/
	public void salvaTransferencia() {

		BancoPeriodo perTransf = serBanco.selecionaPeriodo(bancoCredito, bancoLcto.getDataLcto());

		// se não encontrou periodo para o banco credor, exibe erro
		if (perTransf != null) {

			BancoLcto transferencia = new BancoLcto();
			transferencia.setBancoPeriodo(perTransf);
			transferencia.setOrigemLcto(bancoLcto.getOrigemLcto());
			transferencia.setDataLcto(bancoLcto.getDataLcto());
			transferencia.setTipoLcto(DebitoCredito.CREDITO);
			transferencia.setDocumento(bancoLcto.getDocumento());
			transferencia.setCheque(false);
			transferencia.setValorLcto(bancoLcto.getValorLcto());
			transferencia.setConta(banco.getConta());// conta banco-contrapartida
			transferencia.setHistorico(bancoLcto.getHistorico());

			bancoLcto.setConta(bancoCredito.getConta());
			bancoLcto.setTransferencia(transferencia);

			try {
				serBancoLcto.salvar(bancoLcto);
				resgataPeriodo();
				mensagens.info("Transferencia salva com sucesso!");
				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().validationFailed();
				mensagens.error(e.getMessage());
			}

		} else {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Não existe periodo de lançamento para o banco crédito");
		}

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

	public List<BancoPeriodo> getListaPeriodo() {
		return listaPeriodo;
	}

	public BancoLcto getBancoLcto() {
		return bancoLcto;
	}

	public void setBancoLcto(BancoLcto bancoLcto) {
		this.bancoLcto = bancoLcto;
	}

	public BancoLcto getLctoSelect() {
		return lctoSelect;
	}

	public void setLctoSelect(BancoLcto lctoSelect) {
		this.lctoSelect = lctoSelect;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public boolean isDisplayCheque() {
		return displayCheque;
	}

	public Banco getBancoCredito() {
		return bancoCredito;
	}

	public void setBancoCredito(Banco bancoCredito) {
		this.bancoCredito = bancoCredito;
	}

}
