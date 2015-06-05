/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.HorariosLocal;
import entities.Horario;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.SeccionFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Named(value = "horarioController")
@SessionScoped
public class HorarioController implements Serializable {
    @EJB
    private HorarioFacadeLocal ejbFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private HorariosLocal ejbBusiness;
    
    private List<Horario> items = null;
    private Horario selected;

    public HorariosLocal getEjbBusiness() {
        return ejbBusiness;
    }

    public void setEjbBusiness(HorariosLocal ejbBusiness) {
        this.ejbBusiness = ejbBusiness;
    }
    
    public HorarioController() {
    }

    public HorarioFacadeLocal getFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(HorarioFacadeLocal ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public SeccionFacadeLocal getSeccionFacade() {
        return seccionFacade;
    }

    public void setSeccionFacade(SeccionFacadeLocal seccionFacade) {
        this.seccionFacade = seccionFacade;
    }

    public List<Horario> getItems() {
        return items;
    }

    public void setItems(List<Horario> items) {
        this.items = items;
    }

    public Horario getSelected() {
        return selected;
    }

    public void setSelected(Horario selected) {
        this.selected = selected;
    }
    
    /*
    función que permite asignar una sección a un bloque libre de un profesor, en base a los objetos horario y seccion
    */
    public boolean asignarBloque(Horario horario, Seccion seccion){
        try {
            horario.setSeccion(seccion);
            getFacade().edit(horario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /*
    función que permite asignar una sección a un bloque libre de un profesor, en base a los id del horario y sección
    */
    public boolean asignarBloque(long idHorario, long idSeccion){
        try {
            Horario horario = getFacade().find(idHorario);
            Seccion seccion = getSeccionFacade().find(idSeccion);
            horario.setSeccion(seccion);
            getFacade().edit(horario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Horario> getHorariosOcupados(){
        return getEjbBusiness().findByHorariosNoDisponibles();
    }
    
}
