package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.entidade.Usuario;
import br.com.resvut42.marvin.entidade.UsuarioRoles;
import br.com.resvut42.marvin.enums.Role;
import br.com.resvut42.marvin.servico.SerUsuario;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Usuario
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleUsuario implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private Usuario usuarioEdicao = new Usuario();
	private Usuario usuarioSelect;
	private DualListModel<Role> rolesListModel;
	private Empresa empresaSelect;
	private Empresa empresa;
	private boolean novaEmpresa = false;
	private boolean dados = false;
	private boolean senha = false;
	private String password;

	@Autowired
	SerUsuario serUsuario;
	@Autowired
	private FacesMessages mensagens;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void inicializacao() {
		montarST();
	}

	/****************************************************************************
	 * Salvar os dados no banco
	 ****************************************************************************/
	public void salvar() {
		dados = false;
		senha = false;
		try {
			recuperarRoles();
			serUsuario.salvar(usuarioEdicao);
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
			serUsuario.excluir(usuarioSelect);
			usuarioSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		listaUsuarios = serUsuario.listarTodos();
	}

	/****************************************************************************
	 * Preparar objetos para novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {
		dados = true;
		senha = true;
		empresa = null;	
		novaEmpresa = false;
		usuarioEdicao = new Usuario();
		montarST();
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editCadastro() {
		dados = true;
		senha = false;
		empresa = null;	
		novaEmpresa = false;
		usuarioEdicao = usuarioSelect;
		montarST();
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editSenha() {
		dados = false;
		senha = true;
		empresa = null;	
		novaEmpresa = false;
		usuarioEdicao = usuarioSelect;
		montarST();
	}

	/****************************************************************************
	 * Bloqueia / Desbloqueia registro selecionado na tela
	 ****************************************************************************/
	public void bloqueioDesbloqueio() {

		usuarioEdicao = usuarioSelect;

		if (usuarioEdicao.isBloqueado()) {
			usuarioEdicao.setBloqueado(false);
		} else {
			usuarioEdicao.setBloqueado(true);
		}

		try {
			serUsuario.salvar(usuarioEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata a empresa selecionada no dialogo
	 ****************************************************************************/
	public void empresaSelecionada(SelectEvent event) {
		empresa = new Empresa();
		empresa = (Empresa) event.getObject();	
		novaEmpresa = true;
	}
	
	/****************************************************************************
	 * -- Montar Source e Target das Roles do usuario
	 ****************************************************************************/
	private void montarST() {

		boolean source;
		List<Role> rolesSource = new ArrayList<Role>();
		List<Role> rolesTarget = new ArrayList<Role>();

		Role[] enumRoles = Role.values();
		List<UsuarioRoles> userRoles = usuarioEdicao.getRoles();

		// lista de roles do enum
		for (Role role : enumRoles) {

			source = true;

			// Verifica se a role já está no usuário
			for (UsuarioRoles usuarioRoles : userRoles) {
				if (role.equals(usuarioRoles.getRole())) {
					// se estiver no usuario, adiciona no target
					rolesTarget.add(role);
					source = false;
					break;
				}
			}

			if (source) {
				// se não está no usuario, adiciona no target
				rolesSource.add(role);
			}

		}

		rolesListModel = new DualListModel<Role>(rolesSource, rolesTarget);

	}

	/****************************************************************************
	 * -- Recupera as roles selecionadas
	 ****************************************************************************/
	private void recuperarRoles() {

		List<UsuarioRoles> delUsuarioRoles = new ArrayList<UsuarioRoles>();
		UsuarioRoles newRole = null;

		List<UsuarioRoles> usuarioRoles = usuarioEdicao.getRoles();
		List<Role> rolesTarget = rolesListModel.getTarget();

		// Remover as Roles removida da tela
		for (UsuarioRoles roleUsuario : usuarioRoles) {
			boolean encontrou = false;

			// varre as roles selecionadas
			for (Role roleTarget : rolesTarget) {
				if (roleUsuario.getRole().equals(roleTarget)) {
					encontrou = true;
					break;
				}
			}

			// se a role do usuarioEdição, não está na target, remove
			if (!encontrou) {
				delUsuarioRoles.add(roleUsuario);
			}

		}
		// Remove efetivamete as roles
		for (UsuarioRoles delUsuarioRole : delUsuarioRoles) {
			usuarioRoles.remove(delUsuarioRole);
		}

		// Adicionar as Roles Adicionada na tela
		for (Role roleTarget : rolesTarget) {

			// Se o usuário não tem nenhuma role
			if (usuarioRoles.isEmpty()) {
				newRole = new UsuarioRoles();
				newRole.setRole(roleTarget);
				usuarioEdicao.addRole(newRole);
			}

			// Se a role já estiver no usuário, não adiciona
			boolean add = true;
			for (UsuarioRoles roleUsuario : usuarioRoles) {
				if (roleUsuario.getRole().equals(roleTarget)) {
					add = false;
					break;
				}
			}

			if (add) {
				newRole = new UsuarioRoles();
				newRole.setRole(roleTarget);
				usuarioEdicao.addRole(newRole);
			}

		}

	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	public Usuario getUsuarioSelect() {
		return usuarioSelect;
	}

	public void setUsuarioSelect(Usuario usuarioSelect) {
		this.usuarioSelect = usuarioSelect;
	}

	public DualListModel<Role> getRolesListModel() {
		return rolesListModel;
	}

	public void setRolesListModel(DualListModel<Role> rolesListModel) {
		this.rolesListModel = rolesListModel;
	}

	public boolean isDados() {
		return dados;
	}

	public boolean isSenha() {
		return senha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Empresa getEmpresaSelect() {
		return empresaSelect;
	}

	public void setEmpresaSelect(Empresa empresaSelect) {
		this.empresaSelect = empresaSelect;
	}

	public boolean isNovaEmpresa() {
		return novaEmpresa;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();		
		usuarioEdicao.setSenha(encoder.encode(this.password));
		
	}
	
	
	
}
