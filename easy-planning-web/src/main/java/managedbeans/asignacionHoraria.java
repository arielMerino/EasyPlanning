/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Horario;
import entities.Profesor;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.CoordinacionFacadeLocal;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.ProfesorFacadeLocal;
import sessionbeans.SeccionFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Named(value = "asignacionHoraria")
@SessionScoped
public class asignacionHoraria implements Serializable {
    
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private CoordinacionFacadeLocal coordinacionFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private ProfesorFacadeLocal profesorFacade;
    @EJB
    private HorarioFacadeLocal horarioFacade;
    
    private String planEstudioSelected = "none";
    private int nivelSelected = 0;
    private Asignatura asignaturaSelected = null;
    private Coordinacion coordinacionSelected = null;
    private Seccion seccionSelected = null;
    private Horario horarioSelected = null;
    private Profesor profesorSelected = null;
    private ArrayList<Asignatura> asignaturasPlan = new ArrayList<>();
    private ArrayList<Asignatura> asignaturasNivel = new ArrayList<>();
    private ArrayList<Profesor> profesoresAsignatura = new ArrayList<>();
    private ArrayList<String> bloques = new ArrayList<>();

    public AsignaturaFacadeLocal getAsignaturaFacade() {
        return asignaturaFacade;
    }

    public ProfesorFacadeLocal getProfesorFacade() {
        return profesorFacade;
    }

    public HorarioFacadeLocal getHorarioFacade() {
        return horarioFacade;
    }

    public void setAsignaturaFacade(AsignaturaFacadeLocal asignaturaFacade) {
        this.asignaturaFacade = asignaturaFacade;
    }

    public void setCoordinacionFacade(CoordinacionFacadeLocal coordinacionFacade) {
        this.coordinacionFacade = coordinacionFacade;
    }

    public void setSeccionFacade(SeccionFacadeLocal seccionFacade) {
        this.seccionFacade = seccionFacade;
    }

    public void setProfesorFacade(ProfesorFacadeLocal profesorFacade) {
        this.profesorFacade = profesorFacade;
    }

    public void setHorarioFacade(HorarioFacadeLocal horarioFacade) {
        this.horarioFacade = horarioFacade;
    }

    public String getPlanEstudioSelected() {
        return planEstudioSelected;
    }

    public void setPlanEstudioSelected(String planEstudioSelected) {
        this.planEstudioSelected = planEstudioSelected;
    }

    public int getNivelSelected() {
        return nivelSelected;
    }

    public void setNivelSelected(int nivelSelected) {
        this.nivelSelected = nivelSelected;
    }

    public Asignatura getAsignaturaSelected() {
        return asignaturaSelected;
    }

    public void setAsignaturaSelected(Asignatura asignaturaSelected) {
        this.asignaturaSelected = asignaturaSelected;
    }

    public Coordinacion getCoordinacionSelected() {
        return coordinacionSelected;
    }

    public void setCoordinacionSelected(Coordinacion coordinacionSelected) {
        this.coordinacionSelected = coordinacionSelected;
    }

    public Seccion getSeccionSelected() {
        return seccionSelected;
    }

    public void setSeccionSelected(Seccion seccionSelected) {
        this.seccionSelected = seccionSelected;
    }

    public Horario getHorarioSelected() {
        return horarioSelected;
    }

    public void setHorarioSelected(Horario horarioSelected) {
        this.horarioSelected = horarioSelected;
    }

    public Profesor getProfesorSelected() {
        return profesorSelected;
    }

    public void setProfesorSelected(Profesor profesorSelected) {
        this.profesorSelected = profesorSelected;
    }
    
    public void setAsignaturasPlan(){
        asignaturasPlan = new ArrayList<>();
        List<Asignatura> asignaturas = asignaturaFacade.findAll();
        if(!getPlanEstudioSelected().equals("none")){
            for(Asignatura asg : asignaturas){
                if(asg.getPlanEstudio().equals(getPlanEstudioSelected())){
                    asignaturasPlan.add(asg);
                }
            }
        }
    }
    
    public void setBloques(){
        bloques = new ArrayList<>();
        int contador = 1;
        for (int i = 0; i < 63; i++) {
            if (i%7==0) {
                bloques.add(contador+"");
                contador++;
            }
            else{
                bloques.add("ingresar");
            }
        }
    }
    public ArrayList<String> getBloques(){
        setBloques();
        return bloques;
    }
    
    public void setAsignaturasNivel(){
         asignaturasNivel = new ArrayList<>();
         if(getNivelSelected() != 0){
             for(Asignatura asg : getAsignaturasPlan()){
                 if(asg.getNivel() == getNivelSelected()){
                     asignaturasNivel.add(asg);
                 }
             }
         }
    }
    
    public void setProfesoresAsignatura(){
        profesoresAsignatura = new ArrayList<>();
        if(getAsignaturaSelected() != null){
            for(Profesor prof : getProfesorFacade().findAll()){
                if(prof.getAsignaturas().contains(getAsignaturaSelected())){
                    profesoresAsignatura.add(prof);
                }
            }
        }
    }
    
    public ArrayList<Asignatura> getAsignaturasNivel(){
        setAsignaturasNivel();
        return asignaturasNivel;
    }
    
    public ArrayList<Asignatura> getAsignaturasPlan(){
        return asignaturasPlan;
    }
    
    public ArrayList<Profesor> getProfesoresAsignatura(){
        setProfesoresAsignatura();
        return profesoresAsignatura;
    }
    
    public String getNombreCompleto(Profesor profesor){
        return profesor.getNombre()+" "+profesor.getApellido();
    }
    
    
  
    
    public ArrayList<Integer> getNiveles(){
        ArrayList<Integer> niveles = new ArrayList<>();
        if(!getPlanEstudioSelected().equals("none")){
            setAsignaturasPlan();
            int nivel = 0;
            for(Asignatura asg : getAsignaturasPlan()){
                if(asg.getNivel() > nivel){
                    nivel = asg.getNivel();
                }
            }
            for (int i = 0; i < nivel; i++) {
                niveles.add(i+1);
            }
        }
        return niveles;
    }
    
    
    
    /**
     * Creates a new instance of asignacionHoraria
     */
    public asignacionHoraria() {
    }
    
}
