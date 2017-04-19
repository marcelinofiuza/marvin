package br.com.resvut42.marvin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.resvut42.marvin.entidade.Carteira;

/****************************************************************************
 * Classe de conversão do list Carteira
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
@FacesConverter(value = "converterCarteira")
public class ConverterCarteira implements Converter  {

	private Carteira carteira;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		carteira = (Carteira) value;
		if (carteira != null && carteira.getIdCarteira() != null) {
			component.getAttributes().put(Long.toString(carteira.getIdCarteira()), carteira);
			return Long.toString(carteira.getIdCarteira());
		}
		return null;
	}
	
}
