/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.ProfesoresLocal;
import entities.Profesor;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jano
 */
@FacesValidator("ValidadorRut")
public class ValidadorRut implements Validator{
    
    @EJB
    private ProfesoresLocal ejbFacade;

    public boolean compruebaRut(String rut){
        boolean validacion = false;
        rut = rut.toUpperCase();
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        try {
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
    
    public boolean compruebaExistencia(String rut){
        rut = rut.toUpperCase();
        
        return ejbFacade.findByRut(rut) != null;
    }
    
    public boolean compruebaFormato(String rut){
        return rut.matches("\\d{1,2}\\.\\d{3}\\.\\d{3}\\-[K|k|0-9]");
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(!compruebaRut(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, value + " no es un Rut válido", 
                value + " no es un Rut válido"));
        }
        if(compruebaExistencia(value.toString())){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, value + " ya está registrado", 
                value + " ya está registrado"));
        }
        if(!compruebaFormato(value.toString())){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, value + " no tiene el formato adecuado. RUT debe contener puntos y guión", 
                value + " no tiene el formato adecuado. RUT debe contener puntos y guión"));
        }
    }
}
    
