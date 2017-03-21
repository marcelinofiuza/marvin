package br.com.resvut42.marvin.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

/****************************************************************************
 * Classe de Menssagens
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@Component
public class FacesMessages implements Serializable {

	private static final long serialVersionUID = 1L;

	private void add(String titulo, String message, Severity severity) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage();
		msg.setSummary(titulo);
		msg.setDetail(message);
		msg.setSeverity(severity);
		context.addMessage(null, msg);

	}

	public void info(String message) {
		add(message, "sucesso", FacesMessage.SEVERITY_INFO);
	}

	public void warning(String message) {
		add(message, "Atenção", FacesMessage.SEVERITY_WARN);
	}

	public void error(String message) {
		add(message, "Erro", FacesMessage.SEVERITY_ERROR);
	}

	public void fatal(String message) {
		add("Erro Fatal", message, FacesMessage.SEVERITY_FATAL);
	}
}
