/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Profesor;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.SeccionFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Named(value = "asignaturaController")
@SessionScoped
public class AsignaturaController implements Serializable {
    @EJB
    private AsignaturaFacadeLocal ejbFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    private List<Asignatura> items = null;
    private Asignatura selected;
    
    /**
     * Creates a new instance of AsignaturaController
     */
    public AsignaturaController() {
    }

    public SeccionFacadeLocal getSeccionFacade() {
        return seccionFacade;
    }
    
    public AsignaturaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Asignatura> getItems() {
        return items;
    }

    public void setItems(List<Asignatura> items) {
        this.items = items;
    }

    public Asignatura getSelected() {
        return selected;
    }

    public void setSelected(Asignatura selected) {
        this.selected = selected;
    }
    /*
    carga la lista de asignaturas en items
    */
    public boolean getAsignaturas(){
        try {
            this.items = getFacade().findAll();
            return true;
        } catch (Exception e) {
            return false;
        } 
    }
    
    /*
    retorna un ArrayList de asignatura, donde cada una es prerrequisito de la entrada
    */
    public ArrayList<Asignatura> getPrerrequisitos(long idAsignatura){
        ArrayList<Asignatura> prerrequisitos = new ArrayList<>();
        Asignatura asignatura = getFacade().find(idAsignatura);
        for(Asignatura item : asignatura.getPrerequisitos()){
            prerrequisitos.add(item);
        }
        return prerrequisitos;
    }   
    
    /*
    retorna un ArrayList con todas las asignaturas de un nivel específico
    */
    public ArrayList<Asignatura> getAsignaturasPorNivel(int nivel){
        getAsignaturas();
        ArrayList<Asignatura> asignaturasPorNivel = new ArrayList<>();
        if(items != null){
            for (Asignatura item : items) {
                if (item.getNivel() == nivel) {
                    asignaturasPorNivel.add(item);
                }
            }
        }
        return asignaturasPorNivel;
    }
    
    /*
    retorna un ArrayList con todos los profesores asociados a una asignatura
    */
    public ArrayList<Profesor> getProfesoresAsignatura(long idAsignatura){
        List<Profesor> p = getFacade().find(idAsignatura).getProfesores();
        ArrayList<Profesor> profesores = new ArrayList<>();
        for (Profesor prof : p) {
            profesores.add(prof);
        }
        return profesores;
    }
    
    /*
    obtiene una ArrayList con todas las secciones asociadas a una asignatura, en un año y semestre específico
    */
    public ArrayList<Seccion> getSecciones(long idAsignatura, int ano, int semestre){
        ArrayList<Seccion> seccionesCoordinacion = new ArrayList<>();
        List<Coordinacion> coordinaciones = getFacade().find(idAsignatura).getCoordinaciones();
        List<Seccion> secciones = getSeccionFacade().findAll();
        Coordinacion coordinacion = null;
        for( Coordinacion item : coordinaciones){
            if(item.getAño() == ano && item.getSemestre() == semestre){
                coordinacion = item;
                break;
            }
        }
        if(coordinacion != null){
            for(Seccion seccion : secciones){
                if (seccion.getCoordinacion().getId() == coordinacion.getId()) {
                    seccionesCoordinacion.add(seccion);
                }
            }
        }
        return seccionesCoordinacion;
    }
    
    public String nombres(ArrayList<Asignatura> asignaturas){
        String nom = "";
        for(Asignatura item : asignaturas){
            nom = nom + "\n" + item.getNombre();
        }
        return nom;
    }
}
