package br.com.resvut42.marvin;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class MarvinApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MarvinApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MarvinApplication.class);
	}
	
	@Bean
	public FixedLocaleResolver localResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
}
