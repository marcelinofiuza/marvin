package br.com.resvut42.marvin.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Usuario;
import br.com.resvut42.marvin.entidade.UsuarioRoles;
import br.com.resvut42.marvin.servico.SerUsuario;
/****************************************************************************
 * Classe de configuração do usuário
 * 
 * @author Bob-Odin - 06/02/2017
 ****************************************************************************/
@Component
public class SecurityUserService implements UserDetailsService {

	@Autowired
	SerUsuario serUsuario;
	
	/****************************************************************************
	 * Retornar detalhes do usuário
	 ****************************************************************************/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Usuario usuario = serUsuario.buscarPorCredencial(username);

		if (usuario != null) {

			SecurityUserDetails userDetails = new SecurityUserDetails();
			userDetails.setUsername(usuario.getCredencial());
			userDetails.setPassword(usuario.getSenha());
			userDetails.setContaBloqueada(usuario.isBloqueado());

			for (UsuarioRoles usuarioRole : usuario.getRoles()) {
				userDetails.addAuthorities("ROLE_"+usuarioRole.getRole().toString());
			}

			return userDetails;
			
		}
		
		throw new UsernameNotFoundException("Usuário não cadastrado");

	}

}
