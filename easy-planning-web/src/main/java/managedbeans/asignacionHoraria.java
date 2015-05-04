/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import entities.Asignatura;
import entities.Checklist;
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
import sessionbeans.ChecklistFacadeLocal;
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
    @EJB
    private ChecklistFacadeLocal checklistFacade;
    
    private String planEstudioSelected = "none";
    private int nivelSelected = 0;
    private Asignatura asignaturaSelected = null;
    private Coordinacion coordinacionSelected = null;
    private Seccion seccionSelected = new Seccion();
    private Long seccionId = 0L;
    private String horarioSelected = "";
    private Profesor profesorSelected = null;
    private ArrayList<Asignatura> asignaturasPlan = new ArrayList<>();
    private ArrayList<Asignatura> asignaturasNivel = new ArrayList<>();
    private ArrayList<Profesor> profesoresAsignatura = new ArrayList<>();
    private ArrayList<Seccion> seccionesAsignatura = new ArrayList<>();
    private ArrayList<Long> seccionesIds = new ArrayList<>();
    private ArrayList<Horario> disponibilidadProfesor = new ArrayList<>();
    private String [] horariosSeleccionados;
    private int lastAño = 0;
    private int lastSemestre = 0;

    public Long getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Long seccionId) {
        this.seccionId = seccionId;
    }
    
    

    public CoordinacionFacadeLocal getCoordinacionFacade() {
        return coordinacionFacade;
    }

    public SeccionFacadeLocal getSeccionFacade() {
        return seccionFacade;
    }

    public ChecklistFacadeLocal getChecklistFacade() {
        return checklistFacade;
    }

    public AsignaturaFacadeLocal getAsignaturaFacade() {
        return asignaturaFacade;
    }
    
    public String[] getHorariosSeleccionados() {
        return horariosSeleccionados;
    }

    public void setHorariosSeleccionados(String[] horariosSeleccionados) {
        this.horariosSeleccionados = horariosSeleccionados;
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

    public Seccion getSeccionSelected() {
        return this.seccionSelected;
    }

    public void setSeccionSelected(Seccion seccionSelected) {
        this.seccionSelected = seccionSelected;
    }

    public String getHorarioSelected() {
        return horarioSelected;
    }

    public void setHorarioSelected(String horarioSelected) {
        this.horarioSelected = horarioSelected;
    }

    public Profesor getProfesorSelected() {
        return profesorSelected;
    }

    public void setProfesorSelected(Profesor profesorSelected) {
        this.profesorSelected = profesorSelected;
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
    
    public int getLastAño() {
        setLastAño();
        return lastAño;
    }
    
    public int getLastAño2(){
        return lastAño;
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
            List<Checklist> checklists = checklistFacade.findAll();
            if(checklists.isEmpty()){
            }else{
                for (Checklist ch : checklists) {
                    if(ch.getAsignatura().getId()==getAsignaturaSelected().getId()){
                        profesoresAsignatura.add(ch.getEncuesta().getProfesor());
                    }
                }
            }
        }
    }
    
    public boolean setLastAño() {
        if (getAsignaturasPlan().size() > 0) {
            lastAño = 0;
            List<Coordinacion> coordinaciones = getAsignaturasPlan().get(0).getCoordinaciones();
            for(Coordinacion cord : coordinaciones){
                if(cord.getAño()>lastAño){
                    lastAño = cord.getAño();
                }
            }
            return true;
        }
        return false;
    }

    public int getLastSemestre() {
        setLastSemestre();
        return lastSemestre;
    }

    public boolean setLastSemestre() {
        if (getAsignaturasPlan().size() > 0){
            this.lastSemestre = 0;
            List<Coordinacion> coordinaciones = getAsignaturasPlan().get(0).getCoordinaciones();
            getLastAño();
            for (Coordinacion cord : coordinaciones) {
                if (cord.getAño() == getLastAño2()) {
                    if(cord.getSemestre() > this.lastSemestre){
                        this.lastSemestre = cord.getSemestre();
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public Coordinacion getCoordinacionSelected() {
        setCoordinacionSelected();
        return coordinacionSelected;
    }

    public void setCoordinacionSelected() {
        if(getAsignaturaSelected()!=null){
            List<Coordinacion> coordinaciones = getAsignaturaSelected().getCoordinaciones();
            int sem = getLastSemestre();
            int año = getLastAño2();
            for (Coordinacion cord : coordinaciones) {
                if(cord.getAño() == año && cord.getSemestre() == sem){
                    this.coordinacionSelected = cord;
                }
            }
        }
        else{
            this.coordinacionSelected = null;
        }
    }
    
    public ArrayList<Seccion> getSeccionesAsignatura() {
        setSeccionesAsignatura();
        return seccionesAsignatura;
    }

    public void setSeccionesAsignatura() {
        this.seccionesAsignatura = new ArrayList<>();
        if(this.coordinacionSelected!= null){
            for (Seccion sec : getCoordinacionSelected().getSecciones()) {
                this.seccionesAsignatura.add(sec);
            }
        }
    }

    public ArrayList<Long> getSeccionesIds() {
        cargarSeccionesIds();
        return seccionesIds;
    }

    public void setSeccionesIds(ArrayList<Long> seccionesIds) {
        this.seccionesIds = seccionesIds;
    }
    
    public void cargarSeccionesIds(){
        ArrayList<Long> ids = new ArrayList<>();
        if (getCoordinacionSelected()!=null) {
            for (Seccion s : getCoordinacionSelected().getSecciones()) {
                ids.add(s.getId());
            }
        }
        setSeccionesIds(ids);
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
    
    public void setDisponibilidadProfesor(){
        try{
            System.out.println(this.coordinacionSelected.getAsignatura().getNombre());
            if(!getProfesorSelected().getDisponibilidad().isEmpty()){
                this.disponibilidadProfesor = new ArrayList<>();
                for (Horario h : getProfesorSelected().getDisponibilidad()) {
                    this.disponibilidadProfesor.add(h);
                }
            }
        }catch(Exception e){
            System.out.println("disponibilidad imposible de setear");
        }
    }
    
    public ArrayList<Horario> getDisponibilidadProfesor(){
        setDisponibilidadProfesor();
        return this.disponibilidadProfesor;
    }
    
    public String compararDisponibilidad(String bloque){
        if(this.disponibilidadProfesor.isEmpty()){
            return "";
        }
        for(Horario h : this.disponibilidadProfesor){
            if(h.getBloque().equals(bloque)){
                if(h.getSeccion()!=null)
                    return h.getSeccion().getCoordinacion().getAsignatura().getCodigo()+"-"+h.getSeccion().getCodigo();
                return "Disponible";
            }
        }
        System.out.println("no se encontró nada");
        return "__________";
    }
    
    public void asignarHoras(String[] bloques){
        if(bloques != null)
            System.out.println("bloques: ");
    }
    
    public String getTipo(String bloque){
        if(bloque.charAt(0)=='L')
            return "laboratorio";
        if(bloque.charAt(0)=='T')
            return "teoría";
        return "ejercicios";
    }
    
    public void updateHorario(){
        for(Horario h : this.disponibilidadProfesor){
            if(h.getBloque().equals(this.horarioSelected)){
                h.setSeccion(seccionFacade.find(this.seccionId));
                h.setTipo(getTipo(this.horarioSelected));
                horarioFacade.edit(h);
                System.out.println("se ha actualizado correctamente el bloque horario");
                break;
            }
        }
        System.out.println("no se ha encontrado ningun bloque disponible");
    }
    
    public void liberarHorario(){
        for(Horario h : this.disponibilidadProfesor){
            if(h.getBloque().equals(this.horarioSelected)){
                h.setSeccion(null);
                h.setTipo(null);
                horarioFacade.edit(h);
                break;
            }
        }
    }
    /**
     * Creates a new instance of asignacionHoraria
     */
    public asignacionHoraria() {
    }
    
}
