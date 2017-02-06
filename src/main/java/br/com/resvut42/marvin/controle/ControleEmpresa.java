package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.enums.Estado;
import br.com.resvut42.marvin.enums.RamoAtividade;
import br.com.resvut42.marvin.servico.SerEmpresa;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************/
//Classe controle para View da Tela da Empresa
//Desenvolvido por : Bob-Odin
//Criado em 01/02/2017 
/****************************************************************************/

@Named
@ViewScoped
public class ControleEmpresa implements Serializable {

	/****************************************************************************/
	// Variaveis e Dependências
	/****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<Empresa> listaEmpresas = new ArrayList<Empresa>();
	private Empresa empresaEdicao = new Empresa();
	private Empresa empresaSelect;

	@Autowired
	SerEmpresa serEmpresa;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************/
	// Metodo Salvar
	/****************************************************************************/
	public void salvar() {
		try {
			serEmpresa.salvar(empresaEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************/
	// Metodo Excluir
	/****************************************************************************/
	public void excluir() {
		try {
			serEmpresa.excluir(empresaSelect);
			empresaSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************/
	// Buscar lista dos dados no banco
	/****************************************************************************/
	public void listar() {
		listaEmpresas = serEmpresa.ListarTodos();
	}

	/****************************************************************************/
	// Preparar objetos para novo cadastro
	/****************************************************************************/
	public void novoCadastro() {
		empresaEdicao = new Empresa();
	}

	/****************************************************************************/
	// Atribuir no controle o registro selecionado na tela
	/****************************************************************************/
	public void editCadastro() {
		empresaEdicao = empresaSelect;
	}

	/****************************************************************************/
	// -- Lista de opções de enums
	/****************************************************************************/
	public RamoAtividade[] getRamosAtividade() {
		return RamoAtividade.values();
	}
	
	public Estado[] getEstados(){
		return Estado.values();
	}

	/****************************************************************************/
	// Gets e Sets do controle
	/****************************************************************************/
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa empresaEdicao) {
		this.empresaEdicao = empresaEdicao;
	}

	public Empresa getEmpresaSelect() {
		return empresaSelect;
	}

	public void setEmpresaSelect(Empresa empresaSelect) {
		this.empresaSelect = empresaSelect;
	}

}
