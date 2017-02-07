package br.com.resvut42.marvin.seguranca;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/****************************************************************************
 * Classe de configuração da segurança do software:
 * 
 * @author Bob-Odin - 06/02/2017
 ****************************************************************************/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	SecurityUserService securityUserService;
	
	/****************************************************************************
	 * Monta configuração para autenticação do usuário
	 ****************************************************************************/
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		try {
			
			http
				.userDetailsService(securityUserService)
				.headers()
					.frameOptions().sameOrigin()
			.and()
				.formLogin()
					.loginPage("/R42Login.xhtml").permitAll()
					.failureUrl("/R42Login.xhtml?error").permitAll()			
					.defaultSuccessUrl("/Inicio.xhtml")
			.and()
				.logout().permitAll()
			.and()
				.csrf()
					.disable()
				.authorizeRequests()									
					.antMatchers("/javax.faces.resource/*").permitAll()
					.antMatchers("/resources/*").permitAll()
					.antMatchers("/WEB-INF/**").permitAll()			    
					.antMatchers("/Usuario.xhtml").hasRole("ADMIN")
					.anyRequest().authenticated();
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
			
	}
}
