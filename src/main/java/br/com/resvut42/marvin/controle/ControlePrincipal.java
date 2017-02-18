package br.com.resvut42.marvin.controle;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.entidade.Usuario;
import br.com.resvut42.marvin.servico.SerEmpresa;
import br.com.resvut42.marvin.servico.SerUsuario;

/****************************************************************************
 * Classe controle principal, para View da Telas Iniciais
 * 
 * @author: Bob-Odin - 06/02/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControlePrincipal {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/	
	public HttpSession httpSessao;
	public Usuario usuario;
	public Empresa empresa;
	
	@Autowired
	SerUsuario serUsuario;
	
	@Autowired
	SerEmpresa serEmpresa;
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/	
	@PostConstruct
	public void inicio() {		
		session();
		resgataUsuario();
		resgataEmpresa();
	}
	
	/****************************************************************************
	 * pega sessão corrente
	 ****************************************************************************/		
	private void session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		httpSessao = attr.getRequest().getSession(true); // true == allow create
	}

	/****************************************************************************
	 * pega dados do usuário logado
	 ****************************************************************************/	
	private void resgataUsuario(){
				
		usuario = (Usuario) httpSessao.getAttribute("USUARIO");		
		
		if(usuario == null){
			try {			
				
				SecurityContextImpl sci = (SecurityContextImpl) httpSessao.getAttribute("SPRING_SECURITY_CONTEXT");
				UserDetails user = (UserDetails) sci.getAuthentication().getPrincipal();
				usuario = serUsuario.buscarPorCredencial(user.getUsername());

				//Atualiza data de acesso do usuario
				usuario.setUltimoAcesso(new Date(System.currentTimeMillis()));
				serUsuario.salvar(usuario);

				//limpa senha para deixar na memoria
				usuario.setSenha(null);
				httpSessao.setAttribute("USUARIO", usuario);				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/****************************************************************************
	 * pega dados da empresa
	 ****************************************************************************/	
	private void resgataEmpresa(){
		empresa = (Empresa) httpSessao.getAttribute("EMPRESA");
		
		if(empresa == null){
			List<Empresa> listaEmpresas = serEmpresa.ListarTodos();
			if(!listaEmpresas.isEmpty()){
				empresa = listaEmpresas.get(0);
				httpSessao.setAttribute("EMPRESA", empresa);
			}
		}
		
	}
	/****************************************************************************
	 * Gets e Sets
	 ****************************************************************************/
	
	public Usuario getUsuario() {
		return usuario;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
	
}
