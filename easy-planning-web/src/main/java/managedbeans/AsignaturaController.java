/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AsignaturaFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Named(value = "asignaturaController")
@SessionScoped
public class AsignaturaController implements Serializable {
    @EJB
    private AsignaturaFacadeLocal ejbFacade;
    
    private List<Asignatura> items = null;
    private Asignatura selected;
    
    /**
     * Creates a new instance of AsignaturaController
     */
    public AsignaturaController() {
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
        for (Asignatura item : items) {
            if (item.getNivel() == nivel) {
                asignaturasPorNivel.add(item);
            }
        }
        return asignaturasPorNivel;
    }
    
    public String nombres(ArrayList<Asignatura> asignaturas){
        String nom = "";
        for(Asignatura item : asignaturas){
            nom = nom + "\n" + item.getNombre();
        }
        return nom;
    }
}
