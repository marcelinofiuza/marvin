package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.servico.SerEmpresa;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela da Empresa
 * 
 * @author: Bob-Odin - 01/02/2017
 ****************************************************************************/

@Named
@ViewScoped
public class ControleEmpresa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private Empresa empresaEdicao;

	@Autowired
	SerEmpresa serEmpresa;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Metodo Salvar
	 ****************************************************************************/
	public void salvar() {
		
		try {
			
			serEmpresa.salvar(empresaEdicao);
			mensagens.info("Registro salvo com sucesso!");
			
			//Atualiza memória
			atualizaSessao();
			
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}		
		
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm"));	
		
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void buscar(){
						
		List<Empresa> listaEmpresas = serEmpresa.listarTodos();
		if(!listaEmpresas.isEmpty()){
			empresaEdicao = listaEmpresas.get(0);
		}else{
			empresaEdicao = new Empresa();
		}
		
		RequestContext.getCurrentInstance().execute("PF('wgDados').show();");		
		
	}

	/****************************************************************************
	 * Atualiza a sessão com os novos dados da empresa
	 ****************************************************************************/	
	private void atualizaSessao(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSessao = attr.getRequest().getSession(true); // true == allow create	
		httpSessao.setAttribute("EMPRESA", empresaEdicao);
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa empresaEdicao) {
		this.empresaEdicao = empresaEdicao;
	}

}
