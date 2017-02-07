package br.com.resvut42.marvin.controle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.resvut42.marvin.entidade.Usuario;
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
	
	@Autowired
	SerUsuario serUsuario;
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/	
	@PostConstruct
	public void inicio() {		
		session();
		resgataUsuario();		
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
			SecurityContextImpl sci = (SecurityContextImpl) httpSessao.getAttribute("SPRING_SECURITY_CONTEXT");
			UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
			usuario = serUsuario.buscarPorCredencial(cud.getUsername());		
			httpSessao.setAttribute("USUARIO", usuario);
		}
		
	}
	
	
	/****************************************************************************
	 * Gets e Sets
	 ****************************************************************************/
	
	public Usuario getUsuario() {
		return usuario;
	}

}
