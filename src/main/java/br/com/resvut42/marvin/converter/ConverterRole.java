package br.com.resvut42.marvin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.resvut42.marvin.enums.Role;

/****************************************************************************
 * Classe de conversão do enum Role
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@FacesConverter(value = "converterRole")
public class ConverterRole implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		Role role = Role.valueOf(value);
		return role;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		Role role = (Role) value;
		return role.toString();
	}

}
