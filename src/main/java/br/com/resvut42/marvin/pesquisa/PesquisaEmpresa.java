package br.com.resvut42.marvin.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.servico.SerEmpresa;

/****************************************************************************
 * Classe controle para View de Pesquisa de Empresas
 * 
 * @author: Bob-Odin - 03/02/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaEmpresa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	private String razaoSocial = "";
	private String fantasia = "";

	@Autowired
	SerEmpresa serEmpresa;

	/****************************************************************************
	 * Buscar lista de empresas
	 ****************************************************************************/
	public void listarEmpresas() {
		listaEmpresas = new ArrayList<Empresa>();
		listaEmpresas = serEmpresa.listarPorRazaoSocialOuFantasia(razaoSocial, fantasia);
	}
	
	/****************************************************************************
	 * Retorna o registro selecionado na lista
	 ****************************************************************************/	
	public void selecionar(Empresa empresa){
		RequestContext.getCurrentInstance().closeDialog(empresa);
	}

	/****************************************************************************
	 * Abre o xhtml em forma de dialogo
	 ****************************************************************************/	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", "800");		
		opcoes.put("contentHeight", "450");
		opcoes.put("width", "800");
		opcoes.put("height", "450");

		RequestContext.getCurrentInstance().openDialog("resources/ajudapesquisa/pesquisaEmpresa", opcoes, null);
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

}
