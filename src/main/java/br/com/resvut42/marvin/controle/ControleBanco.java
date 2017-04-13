package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.entidade.BancoContatos;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.entidade.Carteira;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.util.FacesMessages;
import br.com.resvut42.marvin.util.R42Data;

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
	private long nextItem = newItem;
	private List<BancoContatos> listaBancoContatos;

	private List<BancoPeriodo> listaBancoPeriodo;
	private BancoPeriodo bancoPeriodo;
	private boolean periodoValido;

	private List<Carteira> listaBancoCarteira;
	private Carteira bancoCarteira;

	@Autowired
	SerBanco serBanco;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {
		listaBancoContatos = new ArrayList<BancoContatos>();
		conta = new Conta();
	}

	/****************************************************************************
	 * Salvar os dados no banco
	 ****************************************************************************/
	public void salvar() {
		try {
			for (BancoContatos contatosBanco : listaBancoContatos) {
				if (contatosBanco.getIdContato() > newItem) {
					contatosBanco.setIdContato(null);
				}
			}
			bancoEdicao.setConta(conta);
			bancoEdicao.setContatos(null);
			bancoEdicao.setContatos(listaBancoContatos);
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
		listaBancoContatos.clear();
		listaBancoContatos.addAll(bancoEdicao.getContatos());
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
		nextItem++;
		BancoContatos bancoContatos = new BancoContatos();
		bancoContatos.setBanco(bancoEdicao);
		bancoContatos.setIdContato(nextItem);
		listaBancoContatos.add(bancoContatos);
	}

	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(BancoContatos contato) {
		listaBancoContatos.remove(contato);
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = new Conta();
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela de periodo
	 ****************************************************************************/
	public void editPeriodo() {
		serBanco.montaSaldo(bancoSelect);
		if (!bancoSelect.getPeriodos().isEmpty()) {
			listaBancoPeriodo = new ArrayList<>();
			listaBancoPeriodo.addAll(bancoSelect.getPeriodos());
		}
	}

	/****************************************************************************
	 * Adicionar novo periodo em branco
	 ****************************************************************************/
	public void novoPeriodo() {
		bancoPeriodo = new BancoPeriodo();
		bancoPeriodo.setBanco(bancoSelect);
		if (listaBancoPeriodo == null || listaBancoPeriodo.isEmpty()) {
			listaBancoPeriodo = new ArrayList<>();
			bancoPeriodo.setAbertura(true);
		} else {
			bancoPeriodo.setAbertura(false);
			int l = listaBancoPeriodo.size() - 1;
			Date ultData = listaBancoPeriodo.get(l).getDataFinal();
			bancoPeriodo.setDataInicio(R42Data.adicionaDias(ultData, 1));
		}
	}

	/****************************************************************************
	 * Adicionar periodo na lista de periodos
	 ****************************************************************************/
	public void adicionaPeriodo() {
		periodoValido = true;
		// Se a data inicial for mairo que a final - erro
		if (R42Data.inicialMaiorFinal(bancoPeriodo.getDataInicio(), bancoPeriodo.getDataFinal())) {
			periodoValido = false;
			mensagens.error("Data Final menor que Data Inicial!");
		} else {
			for (BancoPeriodo itemPeriodo : listaBancoPeriodo) {
				if (R42Data.conflitoPeriodos(itemPeriodo.getDataInicio(), itemPeriodo.getDataFinal(),
						bancoPeriodo.getDataInicio(), bancoPeriodo.getDataFinal())) {

					periodoValido = false;
					mensagens.error("Periodo informado está em conflito entre "
							+ R42Data.dataToString(itemPeriodo.getDataInicio()) + " e "
							+ R42Data.dataToString(itemPeriodo.getDataFinal()));
					break;

				}
			}
		}

		if (periodoValido) {
			listaBancoPeriodo.add(bancoPeriodo);
			RequestContext.getCurrentInstance().execute("PF('wgDadosPeriodo').hide();");
		}
	}

	/****************************************************************************
	 * Remove um periodo da lista de periodos
	 ****************************************************************************/
	public void removePeriodo(BancoPeriodo bancoPeriodo) {
		if (bancoPeriodo.getLancamentos().size() == 0) {
			for (int i = 0; i < this.listaBancoPeriodo.size(); i++) {
				BancoPeriodo bPeriodo = this.listaBancoPeriodo.get(i);
				if (bPeriodo.getDataInicio().compareTo(bancoPeriodo.getDataInicio()) == 0) {
					this.listaBancoPeriodo.remove(i);
				}
			}
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Necessário excluir os lançamentos antes!");
		}
	}

	/****************************************************************************
	 * Fecha um periodo da lista de periodos
	 ****************************************************************************/
	public void fechaPeriodo(BancoPeriodo bancoPeriodo) {
		for (BancoPeriodo regPeriodo : this.listaBancoPeriodo) {
			if (regPeriodo.getDataInicio().compareTo(bancoPeriodo.getDataInicio()) == 0) {
				regPeriodo.setFechado(true);
			}
		}
	}

	/****************************************************************************
	 * Abre um periodo da lista de periodos
	 ****************************************************************************/
	public void abrePeriodo(BancoPeriodo bancoPeriodo) {
		for (BancoPeriodo regPeriodo : this.listaBancoPeriodo) {
			if (regPeriodo.getDataInicio().compareTo(bancoPeriodo.getDataInicio()) == 0) {
				regPeriodo.setFechado(false);
			}
		}
	}

	/****************************************************************************
	 * Salvar os periodos do banco
	 ****************************************************************************/
	public void salvaPeriodos() {
		try {
			bancoSelect.setPeriodos(listaBancoPeriodo);
			serBanco.salvar(bancoSelect);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela de banco
	 ****************************************************************************/
	public void editCarteira() {
		listaBancoCarteira = new ArrayList<>();
		listaBancoCarteira.addAll(bancoSelect.getCarteiras());
	}	
	
	/****************************************************************************
	 * Adicionar nova carteira em branco
	 ****************************************************************************/
	public void novaCarteira() {
		bancoCarteira = new Carteira();
		bancoCarteira.setBanco(bancoSelect);
	}
	
	/****************************************************************************
	 * Salvar as carteiras do banco
	 ****************************************************************************/
	public void salvaCarteiras() {
		try {
			bancoSelect.setCarteiras(listaBancoCarteira);
			serBanco.salvar(bancoSelect);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
	}
	
	/****************************************************************************
	 * Remove uma carteira da lista de carteiras
	 ****************************************************************************/
	public void removeCarteira(Carteira bancoCarteira) {
		this.listaBancoCarteira.remove(bancoCarteira);
	}	
	
	/****************************************************************************
	 * Adicionar periodo na lista de periodos
	 ****************************************************************************/
	public void adicionaCarteira() {
		listaBancoCarteira.add(bancoCarteira);
		RequestContext.getCurrentInstance().execute("PF('wgDadosCarteira').hide();");
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

	public List<BancoContatos> getListaBancoContatos() {
		return listaBancoContatos;
	}

	public void setListaBancoContatos(List<BancoContatos> listaBancoContatos) {
		this.listaBancoContatos = listaBancoContatos;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<BancoPeriodo> getListaBancoPeriodo() {
		return listaBancoPeriodo;
	}

	public void setListaBancoPeriodo(List<BancoPeriodo> listaBancoPeriodo) {
		this.listaBancoPeriodo = listaBancoPeriodo;
	}

	public BancoPeriodo getBancoPeriodo() {
		return bancoPeriodo;
	}

	public void setBancoPeriodo(BancoPeriodo bancoPeriodo) {
		this.bancoPeriodo = bancoPeriodo;
	}

	public boolean isPeriodoValido() {
		return periodoValido;
	}

	public int getUltimoPeriodo() {
		if (listaBancoPeriodo.isEmpty()) {
			return 0;
		} else {
			return this.listaBancoPeriodo.size() - 1;
		}
	}

	public int getUltimoFechado() {
		int ultimo = -1;
		if (!listaBancoPeriodo.isEmpty()) {
			for (int i = 0; i < listaBancoPeriodo.size(); i++) {
				BancoPeriodo bancoPeriodo = listaBancoPeriodo.get(i);
				if (bancoPeriodo.isFechado()) {
					ultimo = i;
				} else {
					break;
				}
			}
		}
		return ultimo;
	}

	public List<Carteira> getListaBancoCarteira() {
		return listaBancoCarteira;
	}

	public void setListaBancoCarteira(List<Carteira> listaBancoCarteira) {
		this.listaBancoCarteira = listaBancoCarteira;
	}

	public Carteira getBancoCarteira() {
		return bancoCarteira;
	}

	public void setBancoCarteira(Carteira bancoCarteira) {
		this.bancoCarteira = bancoCarteira;
	}

}
