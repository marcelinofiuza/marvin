package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
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
	private ContatosBanco contatosBanco = new ContatosBanco();
	private List<ContatosBanco> listaContatosBanco = new ArrayList<ContatosBanco>();

	@Autowired
	SerBanco serBanco;
	@Autowired
	private FacesMessages mensagens;

	/****************************************************************************
	 * Salvar os dados no banco
	 ****************************************************************************/
	public void salvar() {
		try {			
			bancoEdicao.getContatos().clear();
			bancoEdicao.getContatos().addAll(listaContatosBanco);
						
			serBanco.salvar(bancoEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
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
		bancoEdicao = bancoSelect;
		listaContatosBanco.clear();
		listaContatosBanco.addAll(bancoEdicao.getContatos());
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		listaBancos = serBanco.listarTodos();
	}

	/****************************************************************************
	 * Preparar objetos para novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {
		bancoEdicao = new Banco();
	}

	/****************************************************************************
	 * Adiconar o contato a lista de contatos
	 ****************************************************************************/
	public void addContato() {		
		contatosBanco.setBanco(bancoEdicao);
		listaContatosBanco.add(contatosBanco);
		contatosBanco = new ContatosBanco();
	}

	/****************************************************************************
	 * Remover contato
	 ****************************************************************************/
	public void removeContato(ContatosBanco contato) {
		listaContatosBanco.remove(contato);		
	}

	/****************************************************************************
	 * Limpa contato
	 ****************************************************************************/
	public void limpaContato() {
		contatosBanco = new ContatosBanco();		
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
		return contatosBanco;
	}

	public void setContatosBanco(ContatosBanco contatosBanco) {
		this.contatosBanco = contatosBanco;
	}

	public List<ContatosBanco> getListaContatosBanco() {
		return listaContatosBanco;
	}

	public void setListaContatosBanco(List<ContatosBanco> listaContatosBanco) {
		this.listaContatosBanco = listaContatosBanco;
	}

}
