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
import br.com.resvut42.marvin.entidade.BancoContatos;
import br.com.resvut42.marvin.entidade.BancoSaldo;
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
	private BancoContatos bancoContatos = new BancoContatos();
	private Set<BancoContatos> listaBancoContatos;
	private Set<BancoSaldo> listaBancoSaldo;
	private BancoSaldo saldoEdicao;
	private long idItmContato;

	@Autowired
	SerBanco serBanco;
	@Autowired
	private FacesMessages mensagens;

	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {
		idItmContato = newItem;
		listaBancoContatos = new HashSet<BancoContatos>();
		limpaContato();
	}

	/****************************************************************************
	 * Limpa contato
	 ****************************************************************************/
	public void limpaContato() {
		bancoContatos = new BancoContatos();
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
			bancoEdicao.getContatos().clear();
			bancoEdicao.getContatos().addAll(listaBancoContatos);
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
	 * Atribuir no controle o registro selecionado na tela de periodo
	 ****************************************************************************/	
	public void editPeriodo(){
		listaBancoContatos = new HashSet<BancoContatos>();
		if(!bancoEdicao.getSaldos().isEmpty()){
			listaBancoSaldo.addAll(bancoEdicao.getSaldos());
		}
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
	 * Adicionar novo periodo
	 ****************************************************************************/
	public void novoPeriodo() {
		saldoEdicao = new BancoSaldo();
	}
	
	/****************************************************************************
	 * Adiconar o contato a lista de contatos
	 ****************************************************************************/
	public void addContato() {
		if (bancoContatos.getContato().getNomeContato().isEmpty()) {
			mensagens.error("Nome do contato é obrigatório!");
		} else {
			if (bancoContatos.getIdContato() == null) {
				idItmContato++;
				bancoContatos.setIdContato(idItmContato);
			}
			bancoContatos.setBanco(bancoEdicao);
			listaBancoContatos.add(bancoContatos);
			limpaContato();
		}
	}

	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(BancoContatos contato) {
		listaBancoContatos.remove(contato);
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

	public BancoContatos getBancoContatos() {
		if (bancoContatos == null) {
			bancoContatos = new BancoContatos();
		}
		return bancoContatos;
	}

	public void setBancoContatos(BancoContatos bancoContatos) {
		this.bancoContatos = bancoContatos;
	}

	public Set<BancoContatos> getListaBancoContatos() {
		return listaBancoContatos;
	}

	public void setListaBancoContatos(Set<BancoContatos> listaBancoContatos) {
		this.listaBancoContatos = listaBancoContatos;
	}

	public Set<BancoSaldo> getListaBancoSaldo() {
		return listaBancoSaldo;
	}

	public void setListaBancoSaldo(Set<BancoSaldo> listaBancoSaldo) {
		this.listaBancoSaldo = listaBancoSaldo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public BancoSaldo getSaldoEdicao() {
		return saldoEdicao;
	}

	public void setSaldoEdicao(BancoSaldo saldoEdicao) {
		this.saldoEdicao = saldoEdicao;
	}
	
}
