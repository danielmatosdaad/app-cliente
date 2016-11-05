package com.app.cliente.parametro.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.app.smart.business.parametro.dto.TipoParametroDTO;

@FacesConverter(value = "tipoParametroConverter")
public class TipoParametroConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (context == null) {
			throw new NullPointerException("context");
		}
		if (component == null) {
			throw new NullPointerException("component");
		}

		TipoParametroDTO tipoParametro = null;
		if (value != null && !value.equalsIgnoreCase("") && value.trim().length() > 0) {
			tipoParametro = TipoParametroDTO.get(Integer.valueOf(value));
			if (tipoParametro == null) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown value",
						"The car is unknown!");
				throw new ConverterException(message);
			}
		}
		return tipoParametro;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (component == null) {
			throw new NullPointerException("component");
		}
		if (obj instanceof TipoParametroDTO) {
			return "" + ((TipoParametroDTO) obj).getValue();
		} else {
			return "" + TipoParametroDTO.INDEFINIDO.getValue();
		}
	}

}
