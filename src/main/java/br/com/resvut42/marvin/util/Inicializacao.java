package br.com.resvut42.marvin.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Usuario;
import br.com.resvut42.marvin.entidade.UsuarioRoles;
import br.com.resvut42.marvin.enums.Role;
import br.com.resvut42.marvin.servico.SerUsuario;

@Component
public class Inicializacao {

	@Autowired
	SerUsuario serUsuario;
	
	@EventListener
	public void usuarioPadrao(ApplicationReadyEvent event){
		List<Usuario> usuarios = serUsuario.listarTodos();
		if(usuarios.isEmpty()){
			try {
				serUsuario.salvar(gerarUsuario());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Usuario gerarUsuario(){

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		UsuarioRoles roles = new UsuarioRoles();
		roles.setRole(Role.ADMIN);
		
		Usuario usuario = new Usuario();
		usuario.setCredencial("RESVUT");
		usuario.setSenha(encoder.encode("42"));
		usuario.setNome("RESPOSTA PARA VIDA O UNIVERSO E TUDO MAIS");
		usuario.setBloqueado(false);
		usuario.addRole(roles);
		
		return usuario;
	}
}
