/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.converter;

import br.com.tcc.controller.ControlConta;
import br.com.tcc.modal.TblConta;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Jp
 */
@FacesConverter(value = "tblConta")
public class ConverterTblConta implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (value != null) {
            if (value.toUpperCase().equals("TODOS")) {
                return null;
            } else {
                return new ControlConta().getById(Integer.parseInt(value));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {
        if (object != null && object instanceof TblConta && ((TblConta) object).getId() != null) {
            if (object.toString().equals("0")) {
                return "0";
            } else {
                return ((TblConta) object).getId().toString();
            }
        }
        return null;
    }
}
