package br.com.resvut42.marvin.seguranca;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/****************************************************************************
 * Classe de detalhes do usuário
 * 
 * @author Bob-Odin - 06/02/2017
 ****************************************************************************/
public class SecurityUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Boolean accountNonLocked;
	private ArrayList<SecurityGrantedAuthority> authorities;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setContaBloqueada(Boolean bloqueado) {
		this.accountNonLocked = !bloqueado;
	}

	public void addAuthorities(String role) {
		if (this.authorities == null) {
			authorities = new ArrayList<SecurityGrantedAuthority>();
		}
		SecurityGrantedAuthority roles = new SecurityGrantedAuthority();
		roles.setRole(role);
		authorities.add(roles);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
