package br.com.resvut42.marvin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.resvut42.marvin.entidade.BancoPeriodo;

/****************************************************************************
 * Classe de conversão do list Periodo
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@FacesConverter(value = "converterPeriodo")
public class ConverterPeriodo implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		BancoPeriodo bp = (BancoPeriodo) value;
		if (bp != null) {
			component.getAttributes().put(Long.toString(bp.getIdPeriodo()), bp);
			return Long.toString(bp.getIdPeriodo());
		}
		return null;
	}

}
