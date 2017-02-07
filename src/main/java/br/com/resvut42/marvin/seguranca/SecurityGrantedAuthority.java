package br.com.resvut42.marvin.seguranca;

import org.springframework.security.core.GrantedAuthority;
/****************************************************************************
 * Classe de configuração da Role
 * 
 * @author Bob-Odin - 06/02/2017
 ****************************************************************************/
public class SecurityGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}

}
