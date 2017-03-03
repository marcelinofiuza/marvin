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

	@Autowired
	SerBanco serBanco;
	@Autowired
	private FacesMessages mensagens;

	/****************************************************************************
	 * Salvar os dados no banco
	 ****************************************************************************/
	public void salvar() {
		try {
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

}
