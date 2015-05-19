/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import entities.Asignatura;
import entities.Coordinacion;
import entities.ParamSemestreAno;
import entities.Profesor;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELException;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;
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
    @EJB
    private AsignaturasLocal asignaturaBusiness;
    @EJB
    private ParamSemestreAnioFacadeLocal ejbParam;
    
    private List<Asignatura> items = null;
    private Asignatura selected;
    private String planEstudios = "todos los planes";
    private int nivel = 0;
    private List<Asignatura> asignaturasFiltradas;
    
    /**
     * Creates a new instance of AsignaturaController
     */
    public AsignaturaController() {
    }

    public List<Asignatura> getAsignaturasFiltradas() {
        return asignaturasFiltradas;
    }

    public void setAsignaturasFiltradas(List<Asignatura> asignaturasFiltradas) {
        this.asignaturasFiltradas = asignaturasFiltradas;
    }

    public SeccionFacadeLocal getSeccionFacade() {
        return seccionFacade;
    }

    public String getPlanEstudios() {
        return planEstudios;
    }

    public void setPlanEstudios(String planEstudios) {
        this.planEstudios = planEstudios;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    
    public List<Asignatura> getListaAsignaturas(){
        return getFacade().findAll();
        
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
    
    public List<Profesor> getProfesoresAsignatura2(long idAsignatura){
        try{
            return getFacade().find(idAsignatura).getProfesores();
        }
        catch(EJBException | ELException e){
            return new ArrayList();
        }
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
            if(item.getAnio() == ano && item.getSemestre() == semestre){
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
    
    public Integer bloquesTotales(Long asignaturaId){
        Integer teoria = getFacade().find(asignaturaId).getTeoria();
        Integer ejercicios = getFacade().find(asignaturaId).getEjercicios();
        Integer lab = getFacade().find(asignaturaId).getLaboratorio();
        Integer totalHoras = teoria+ejercicios+lab;
        return totalHoras;
    }
    
    public ArrayList<Asignatura> getAsignaturasByPlan(String plan, int nivel){
        ArrayList<Asignatura> asignaturasByPlan = new ArrayList<>();
        List<Asignatura> asignaturas = getListaAsignaturas();
        if (plan.equalsIgnoreCase("todos los planes")) {
            for (Asignatura asignatura : asignaturas) {
                asignaturasByPlan.add(asignatura);
            }
            if (nivel != 0) {
                asignaturasByPlan = getAsignaturasPorNivel(asignaturasByPlan, nivel);
            }
            return asignaturasByPlan;
        }
        for (Asignatura asg : asignaturas) {
            if (asg.getPlanEstudio().equals(plan)) {
                asignaturasByPlan.add(asg);
            }
            if (nivel != 0) {
                asignaturasByPlan = getAsignaturasPorNivel(asignaturasByPlan, nivel);
            }
        }
        return asignaturasByPlan;
    }
    
    /*
    retorna un ArrayList con todas las asignaturas de un nivel específico
    */
    public ArrayList<Asignatura> getAsignaturasPorNivel(ArrayList<Asignatura> asignaturas, int nivel){
        ArrayList<Asignatura> asignaturasPorNivel = new ArrayList<>();
        for (Asignatura item : asignaturas) {
            if (item.getNivel() == nivel) {
                asignaturasPorNivel.add(item);
            }
        }
        return asignaturasPorNivel;
    }
    
    
    public ArrayList<String> getPlanesDeEstudio(){
        ArrayList<String> planesEstudio = new ArrayList<>();
        List<Asignatura> asignaturas = getFacade().findAll();
        for (Asignatura asg : asignaturas){
            if(!planesEstudio.contains(asg.getPlanEstudio())){
                planesEstudio.add(asg.getPlanEstudio());
            }
        }
        return planesEstudio;
    }
    
    public ArrayList<Integer> getMaxNivel(){
        int nivel = 0;
        ArrayList<Integer> niveles = new ArrayList<>();
        List<Asignatura> asignaturas = getListaAsignaturas();
        if(getPlanEstudios().equalsIgnoreCase("todos los planes")){
            for( Asignatura asg : asignaturas){
                if(asg.getNivel() > nivel){
                    nivel = asg.getNivel();
                }
            }
        }
        for( Asignatura asg : asignaturas){
            if(asg.getNivel() > nivel && asg.getPlanEstudio().equals(getPlanEstudios())){
                nivel = asg.getNivel();
            }
        }
        for (int i = 0; i < nivel; i++) {
            niveles.add(i+1);
        }
        return niveles;
    }
    
    public Asignatura findByAsignaturaAsignada(Long id_profesor){
        ParamSemestreAno semAnio = ejbParam.find(Long.parseLong(1+""));
        return asignaturaBusiness.findByAsignaturaAsignada(id_profesor, semAnio.getAnoActual(), semAnio.getSemestreActual());
    }
}
