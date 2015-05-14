/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import business.CarrerasLocal;
import business.CoordinacionesLocal;
import business.HorariosLocal;
import business.SeccionesLocal;
import entities.Asignatura;
import entities.Coordinacion;
import entities.Horario;
import entities.Profesor;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.CarreraFacadeLocal;
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
    @EJB
    private AsignaturasLocal asignaturasBusiness;
    @EJB
    private CarrerasLocal carrerasBusiness;
    @EJB
    private CarreraFacadeLocal carreraFacade;
    @EJB
    private HorariosLocal horariosBusiness;
    @EJB
    private SeccionesLocal seccionesBusiness;
    @EJB
    private CoordinacionesLocal coordinacionesBusiness; 
           
    private int carreraSelected = 0;
    private String planEstudioSelected = "none";
    private int nivelSelected = 0;
    private long asignaturaSelected;
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
    private int añoSelected = Calendar.getInstance().get(Calendar.YEAR);
    private int semestreSelected = 1;

    public int getCarreraSelected() {
        return carreraSelected;
    }

    public void setCarreraSelected(int carreraSelected) {
        this.carreraSelected = carreraSelected;
    }

    public HorariosLocal getHorariosBusiness() {
        return horariosBusiness;
    }

    public void setHorariosBusiness(HorariosLocal horariosBusiness) {
        this.horariosBusiness = horariosBusiness;
    }
    
    public Long getSeccionId() {
        return seccionId;
    }

    public CarreraFacadeLocal getCarreraFacade() {
        return carreraFacade;
    }

    public CoordinacionesLocal getCoordinacionesBusiness() {
        return coordinacionesBusiness;
    }

    public void setCoordinacionesBusiness(CoordinacionesLocal coordinacionesBusiness) {
        this.coordinacionesBusiness = coordinacionesBusiness;
    }

    public SeccionesLocal getSeccionesBusiness() {
        return seccionesBusiness;
    }

    public void setSeccionesBusiness(SeccionesLocal seccionesBusiness) {
        this.seccionesBusiness = seccionesBusiness;
    }

    public void setCarreraFacade(CarreraFacadeLocal carreraFacade) {
        this.carreraFacade = carreraFacade;
    }
    
    public void setSeccionId(Long seccionId) {
        this.seccionId = seccionId;
    }

    public AsignaturasLocal getAsignaturasBusiness() {
        return asignaturasBusiness;
    }

    public void setAsignaturasBusiness(AsignaturasLocal asignaturasBusiness) {
        this.asignaturasBusiness = asignaturasBusiness;
    }

    public CarrerasLocal getCarrerasBusiness() {
        return carrerasBusiness;
    }

    public void setCarrerasBusiness(CarrerasLocal carrerasBusiness) {
        this.carrerasBusiness = carrerasBusiness;
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

    public long getAsignaturaSelected() {
        return asignaturaSelected;
    }

    public void setAsignaturaSelected(long asignaturaSelected) {
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
    
    public String getNombreCompleto(Profesor profesor){
        return profesor.getNombre()+" "+profesor.getApellido();
    }
    
    public int getAñoSelected() {
        return añoSelected;
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
    
    public void setAñoSelected(int añoSelected) {
        this.añoSelected = añoSelected;
    }

    public int getSemestreSelected() {
        return this.semestreSelected;
    }

    public void setSemestreSelected(int semestreSelected) {
        this.semestreSelected = semestreSelected;
    }
    
    public Coordinacion getCoordinacionSelected() {
        return coordinacionSelected;
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

    public void setCoordinacionSelected(Coordinacion coordinacionSelected) {
        this.coordinacionSelected = coordinacionSelected;
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
    
    public List<Integer> añosDisponibles(){
        Calendar fecha = Calendar.getInstance();
        List<Integer> años = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            años.add(i + fecha.get(Calendar.YEAR));
        }
        return años;
    }
    
    public String getCodigoAsgSeccionByBloque(String bloque){
        try{
            Horario result = horariosBusiness.findBybloqueCarreraPlanNivelAñoYSemestre(bloque, carreraSelected, planEstudioSelected, nivelSelected, añoSelected, semestreSelected);
            if (result == null)
                return "";
            return result.getSeccion().getCoordinacion().getAsignatura().getCodigo()+"-"+result.getSeccion().getCodigo();
        }catch(NullPointerException e){
            return "";
        }
    }
    
    public void verificarSecciones(int carrera, String plan, int año, int semestre){
        List<Seccion> seccionesSemestre = seccionesBusiness.findBySemestreAñoCarreraPlan(carrera, plan, año, semestre);
        if (seccionesSemestre.size() == 0){
            List<Asignatura> asignaturasPlan = asignaturasBusiness.findByCarreraAndPlan(carrerasBusiness.findByCodigo(carrera).getNombre(), plan);
            if (asignaturasPlan.size() > 0){
                for (Asignatura asg : asignaturasPlan){
                    Coordinacion c = new Coordinacion();
                    c.setAsignatura(asg);
                    c.setAño(año);
                    c.setSemestre(semestre);
                    c.setCantAlumnosEstimado(0);
                    c.setCantAlumnosReal(0);
                    c.setSecciones(null);
                    coordinacionFacade.create(c);
                }
                for (Asignatura asg : asignaturasPlan){
                    Coordinacion c = coordinacionesBusiness.findByAsignaturaAndAñoAndSemestre(asg, año, semestre);
                    if (asg.getTeoria() > 0){
                        Seccion s = new Seccion();
                        s.setCoordinacion(c);
                        s.setCodigo("A1");
                        seccionFacade.create(s);
                    }
                    if (asg.getEjercicios() > 0 ){
                        Seccion s = new Seccion();
                        s.setCoordinacion(c);
                        s.setCodigo("E1");
                        seccionFacade.create(s);
                    }
                    if (asg.getLaboratorio() > 0){
                        Seccion s= new Seccion();
                        s.setCoordinacion(c);
                        s.setCodigo("L1");
                        seccionFacade.create(s);
                    }
                 }
            }
        }
    }
    
    /**
     * Creates a new instance of asignacionHoraria
     */
    
    public asignacionHoraria() {
    }    
}
