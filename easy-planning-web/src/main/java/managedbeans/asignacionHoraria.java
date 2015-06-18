/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import business.CarrerasLocal;
import business.ChecklistsLocal;
import business.CoordinacionesLocal;
import business.HorariosLocal;
import business.ProfesoresLocal;
import business.SeccionesLocal;
import entities.Asignatura;
import entities.Coordinacion;
import entities.Horario;
import entities.ParamSemestreAno;
import entities.Profesor;
import entities.Seccion;
import entities.VersionPlan;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import managedbeans.util.JsfUtil;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.CarreraFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;
import sessionbeans.CoordinacionFacadeLocal;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;
import sessionbeans.ProfesorFacadeLocal;
import sessionbeans.SeccionFacadeLocal;
import sessionbeans.VersionPlanFacadeLocal;

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
    @EJB
    private ChecklistsLocal checklistBusiness;
    @EJB
    private ProfesoresLocal profesoresBusiness;
    @EJB
    private VersionPlanFacadeLocal versionFacade;
    @EJB
    private ParamSemestreAnioFacadeLocal paramSemAno;
           
    private int carreraSelected = 0;
    private long planEstudioSelected = 0L; //version plan
    private int nivelSelected = 0;
    private long asignaturaSelected = 0L;
    private Coordinacion coordinacionSelected = null;
    private Seccion seccionSelected = new Seccion();
    private Long seccionId=0L;
    private String horarioSelected = "";
    private String profesorSelected = "";
    private int anioSelected = Calendar.getInstance().get(Calendar.YEAR);
    private int semestreSelected = 1;
    private String bloqueSelected;
    private int asignar = 0;
    private boolean muestraBoton = false;
    private long versionId=0L;
    
    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public boolean isMuestraBoton() {
        return muestraBoton;
    }

    public ParamSemestreAnioFacadeLocal getParamSemAno() {
        return paramSemAno;
    }

    public void setParamSemAno(ParamSemestreAnioFacadeLocal paramSemAno) {
        this.paramSemAno = paramSemAno;
    }

    public void setMuestraBoton(boolean muestraBoton) {
        this.muestraBoton = muestraBoton;
    }

    public int getCarreraSelected() {
        return carreraSelected;
    }

    public void setCarreraSelected(int carreraSelected) {
        this.carreraSelected = carreraSelected;
    }

    public HorariosLocal getHorariosBusiness() {
        return horariosBusiness;
    }

    public ProfesoresLocal getProfesoresBusiness() {
        return profesoresBusiness;
    }

    public void setProfesoresBusiness(ProfesoresLocal profesoresBusiness) {
        this.profesoresBusiness = profesoresBusiness;
    }

    public void setHorariosBusiness(HorariosLocal horariosBusiness) {
        this.horariosBusiness = horariosBusiness;
    }

    public ChecklistsLocal getChecklistBusiness() {
        return checklistBusiness;
    }

    public void setChecklistBusiness(ChecklistsLocal checklistBusiness) {
        this.checklistBusiness = checklistBusiness;
    }

    public String getProfesorSelected() {
        return profesorSelected;
    }

    public void setProfesorSelected(String profesorSelected) {
        this.profesorSelected = profesorSelected;
    }

    public String getBloqueSelected() {
        return bloqueSelected;
    }

    public void setBloqueSelected(String bloqueSelected) {
        this.bloqueSelected = bloqueSelected;
        Horario h = horariosBusiness.findBybloqueCarreraPlanNivelAnioYSemestre(bloqueSelected, planEstudioSelected, nivelSelected, anioSelected, semestreSelected);
        if (h != null){
            if(h.getProfesor() != null)
                profesorSelected = h.getProfesor().getRutProfesor();
            seccionId = h.getSeccion().getId();
        }
    }
    public List<Integer> findNivelesByPlan(long idVersionPlan){
        List<Asignatura> asgs = asignaturasBusiness.findByCarreraAndPlan(idVersionPlan);
        List<Integer> salida = new ArrayList<>();
        for ( Asignatura a : asgs) {
            if ( !salida.contains(a.getNivel()))
                salida.add(a.getNivel());
        }
        return salida;
    }

    public int getAsignar() {
        return asignar;
    }

    public void setAsignar(int asignar) {
        this.setMuestraBoton(false);
        this.asignar = asignar;
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

    public long getPlanEstudioSelected() {
        return planEstudioSelected;
    }

    public VersionPlanFacadeLocal getVersionFacade() {
        return versionFacade;
    }

    public void setVersionFacade(VersionPlanFacadeLocal versionFacade) {
        this.versionFacade = versionFacade;
    }
    
    public void setPlanEstudioSelected(long planEstudioSelected) {
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
    
    public String getNombreCompleto(Profesor profesor){
        return profesor.getNombre()+" "+profesor.getApellido();
    }
    
    public int getAnioSelected() {
        this.anioSelected = this.paramSemAno.find(1L).getAnoActual();
        return anioSelected;
    }
    
    public void setAnioSelected(int anioSelected) {
        this.anioSelected = anioSelected;
    }

    public int getSemestreSelected() {
        this.semestreSelected = this.paramSemAno.find(1L).getSemestreActual();
        return this.semestreSelected;
    }

    public void setSemestreSelected(int semestreSelected) {
        this.semestreSelected = semestreSelected;
    }
    
    public Coordinacion getCoordinacionSelected() {
        return coordinacionSelected;
    }

    public void setCoordinacionSelected(Coordinacion coordinacionSelected) {
        this.coordinacionSelected = coordinacionSelected;
    }
    public boolean validarAsignatura(){
        if (this.asignaturaSelected == 0L){
            return false;
        }
        return true;
    }
    
    public long resetAsignatura(){
        return 0L;
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
    
    public List<Integer> aniosDisponibles(){
        Calendar fecha = Calendar.getInstance();
        List<Integer> anios = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            anios.add(i + fecha.get(Calendar.YEAR));
        }
        return anios;
    }
    
    public String getCodigoAsgSeccionByBloque(String bloque){
        try{
            Horario result = horariosBusiness.findBybloqueCarreraPlanNivelAnioYSemestre(bloque, planEstudioSelected, nivelSelected, anioSelected, semestreSelected);
            if (result == null){
                return "";
            }
            
            if(result.getSeccion().getCoordinacion().getAsignatura().getAlias() == null){
                return result.getSeccion().getCoordinacion().getAsignatura().getCodigo()+"-"+result.getSeccion().getCodigo();
            }
            else{
                return result.getSeccion().getCoordinacion().getAsignatura().getAlias()+"-"+result.getSeccion().getCodigo();
            }
        }catch(NullPointerException e){
            return "";
        }
    }
    
    public List<Profesor> getProfesoresDisponibles(){
        List<String> ids = new ArrayList<>();
        if (asignaturaSelected != 0L){
            ids = checklistBusiness.findProfesorByAsgAnioSemestre(asignaturaSelected, anioSelected, semestreSelected);
        }
        List<Profesor> profesores = new ArrayList<>();
        for (String id : ids){
            profesores.add(profesorFacade.find(id));
        }
        return profesores;
    }
    
    public long anularProfesor(){
        return 0L;
    }
    
    public String getProfesorByBloque(String bloque){
        try{
            Horario result = horariosBusiness.findBybloqueCarreraPlanNivelAnioYSemestre(bloque, planEstudioSelected, nivelSelected, anioSelected, semestreSelected);
            if (result == null){
                return "";
            }
            if(result.getProfesor().getAlias() == null){
                return "- "+result.getProfesor().getNombre()+" "+result.getProfesor().getApellido();            
            }
            else{
                return "- "+result.getProfesor().getAlias();
            }
        }catch(NullPointerException e){
            return "";
        }
    }
    
    public List<Profesor> getDisponiblesByBloque(String bloque){
        List<Profesor> disponiblesBloque = profesoresBusiness.findDisponiblesByBloque(bloque);
        List<Profesor> disponiblesFinal = new ArrayList<>();
        if (asignaturaSelected != 0L){
            List<String> disponiblesPorAsignatura = checklistBusiness.findProfesorByAsgAnioSemestre(asignaturaSelected, anioSelected, semestreSelected);
            for (String id : disponiblesPorAsignatura){
                Profesor aux = profesorFacade.find(id);
                if (disponiblesBloque.contains(aux))
                    disponiblesFinal.add(aux);
            }
        }
        return disponiblesFinal;
    }
    
    public String obtenerBloquesDisponibles(){
        List<String> disponibles = new ArrayList<>();
        String salida = "";
        List<String> profesoresAsignatura = checklistBusiness.findProfesorByAsgAnioSemestre(asignaturaSelected, anioSelected, semestreSelected);
        for (String id : profesoresAsignatura){
            List<Horario> bloques = horariosBusiness.findDisponiblesByProfesorId(id);
            for (Horario h : bloques){
                if (!disponibles.contains(h.getBloque()))
                    disponibles.add(h.getBloque());
            }
        }
        
        disponibles.sort(null);

        for (String s : disponibles){
            salida += s+" ";
        }
        return salida;
    }
    
    public void limpiarBloqueYprofesor(){
        this.profesorSelected = "";
        this.seccionId = 0L;
        this.setMuestraBoton(false);
    }
    
    public List<Long> findPlanesByCodigoCarrera(long idCarrera){ //versiones
        List<VersionPlan> versiones = versionFacade.findAll();
        List<Long> ids = new ArrayList<>();
        AsignaturaController instance = new AsignaturaController();
        for (VersionPlan v : versiones){
            if (v.getPlanEstudio().getCarrera().getId().equals(idCarrera) && instance.contarVersionesNoVacias(v.getId()) > 0)
                ids.add(v.getId());
        }
        return ids;
    }
    
    
    public void eliminarHorario(){
        Horario h = horariosBusiness.findBybloqueCarreraPlanNivelAnioYSemestre(bloqueSelected, planEstudioSelected, nivelSelected, anioSelected, semestreSelected);
        if (h != null)
            horarioFacade.remove(h);
        limpiarBloqueYprofesor();
        this.setMuestraBoton(false);
    }
    
    public void asignar(){
        try{
            Horario h = horariosBusiness.findBybloqueCarreraPlanNivelAnioYSemestre(bloqueSelected, planEstudioSelected, nivelSelected, anioSelected, semestreSelected);
            if (this.asignar != 0){
                if (h == null){
                    h = new Horario();
                    h.setSeccion(seccionFacade.find(seccionId));
                    h.setBloque(bloqueSelected);
                    if (seccionFacade.find(seccionId).getCodigo().charAt(0)=='E')
                        h.setTipo("E");
                    if (seccionFacade.find(seccionId).getCodigo().charAt(0)=='L')
                        h.setTipo("L");
                    else
                        h.setTipo("T");
                    if (profesorSelected != null){
                        if (!"".equals(profesorSelected)) {
                            h.setProfesor(profesorFacade.find(profesorSelected));
                        } else {
                        }
                    }
                    horarioFacade.create(h);
                    this.asignar = 0;

                    this.profesorSelected = "";
                    this.seccionId = 0L;
                }
                else{
                    h.setBloque(bloqueSelected);
                    h.setSeccion(seccionFacade.find(seccionId));
                    if (seccionFacade.find(seccionId).getCodigo().charAt(0)=='E')
                        h.setTipo("E");
                    if (seccionFacade.find(seccionId).getCodigo().charAt(0)=='L')
                        h.setTipo("L");
                    else
                        h.setTipo("T");
                    if (profesorSelected != null){
                        if (!"".equals(profesorSelected))
                            h.setProfesor(profesorFacade.find(profesorSelected));
                        if ("".equals(profesorSelected))
                            h.setProfesor(null);
                    }
                    if (profesorSelected == null){
                        h.setProfesor(null);
                    }
                    horarioFacade.edit(h);
                    this.asignar = 0;

                    this.profesorSelected = "";
                    this.seccionId = 0L;
                }
            }
            else{

                this.profesorSelected = "";
                this.seccionId = 0L;
            }    
        }
        catch(Exception e){
            JsfUtil.addErrorMessage("EEEEERRRROOOOOOR :C");
        }
        
    }
    
    public void agregaSeccionTeoria(){
        Asignatura asignatura = getAsignaturaFacade().find(getAsignaturaSelected());
        if(asignatura.getTeoria() > 0 || asignatura.getEjercicios() > 0){
            if(coordinacionesBusiness.findByAsignaturaAndAnioAndSemestre(asignatura, getAnioSelected(), getSemestreSelected()) == null){
                Coordinacion coordinacion = new Coordinacion();
                coordinacion.setAsignatura(asignatura);
                coordinacion.setAnio(getAnioSelected());
                coordinacion.setSemestre(getSemestreSelected());
                coordinacion.setCantAlumnosEstimado(0);
                coordinacion.setCantAlumnosReal(0);
                coordinacionFacade.create(coordinacion);
            }

            Coordinacion coord = coordinacionesBusiness.findByAsignaturaAndAnioAndSemestre(asignatura, getAnioSelected(), getSemestreSelected());
            List<Seccion> secciones = getSeccionesBusiness().findByAsignaturaAnioYSemestre(asignatura.getId(), getAnioSelected(), getSemestreSelected());
            List<Seccion> seccionesTeoria = new ArrayList();
            for(Seccion seccion : secciones){
                if(seccion.getCodigo().equals("A-1") || seccion.getCodigo().equals("B-2") || seccion.getCodigo().equals("C-3") || seccion.getCodigo().equals("D-4") || seccion.getCodigo().equals("E-5") || seccion.getCodigo().equals("F-6") || seccion.getCodigo().equals("G-7") || seccion.getCodigo().equals("H-8") || seccion.getCodigo().equals("I-9") || seccion.getCodigo().equals("J-10")){
                    seccionesTeoria.add(seccion);
                }
            }
            switch(seccionesTeoria.size()){
                case 0:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("A-1");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección A-1 creada con éxito");
                    break;
                }
                case 1:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("B-2");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección B-2 creada con éxito");
                    break;                
                }
                case 2:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("C-3");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección C-3 creada con éxito");
                    break;                
                }
                case 3:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("D-4");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección D-4 creada con éxito");
                    break;                
                }
                case 4:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("E-5");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección E-5 creada con éxito");
                    break;                
                }
                case 5:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("F-6");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección F-6 creada con éxito");
                    break;                
                }
                case 6:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("G-7");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección G-7 creada con éxito");
                    break;                
                }
                case 7:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("H-8");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección H-8 creada con éxito");
                    break;                
                }
                case 8:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("I-9");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección I-9 creada con éxito");
                    break;                
                }
                case 9:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("J-10");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección J-10 creada con éxito");
                    break;                
                }
                default:{
                    JsfUtil.addErrorMessage("No se pueden agregar más secciones de teoría");
                }
            }    
        }
        else{
            JsfUtil.addErrorMessage("Ésta asignatura no posee horas de teoría o ejercicios");
        }
        
        
    }
    
    public void quitarSeccionTeoria(){
        Asignatura asignatura = getAsignaturaFacade().find(getAsignaturaSelected());
        List<Seccion> secciones = getSeccionesBusiness().findByAsignaturaAnioYSemestre(asignatura.getId(), getAnioSelected(), getSemestreSelected());
        List<Seccion> seccionesTeoria = new ArrayList();
        for(Seccion seccion : secciones){
            if(seccion.getCodigo().equals("A-1") || seccion.getCodigo().equals("B-2") || seccion.getCodigo().equals("C-3") || seccion.getCodigo().equals("D-4") || seccion.getCodigo().equals("E-5") || seccion.getCodigo().equals("F-6") || seccion.getCodigo().equals("G-7") || seccion.getCodigo().equals("H-8") || seccion.getCodigo().equals("I-9") || seccion.getCodigo().equals("J-10")){
                seccionesTeoria.add(seccion);
            }
        }
        int last = seccionesTeoria.size();
        if(last > 0){
            try{
                getSeccionFacade().remove(seccionesTeoria.get(last-1));
                JsfUtil.addSuccessMessage("Última sección de teoría borrada con éxito");
            }            
            catch(Exception e){
                JsfUtil.addErrorMessage("No se puede eliminar la última sección pues ésta fue asignada");
            }
        }
        else{
            JsfUtil.addErrorMessage("Ésta asignatura no posee horas de teoría");
        }
        
    }
    
    public void agregaSeccionLaboratorio(){
        Asignatura asignatura = getAsignaturaFacade().find(getAsignaturaSelected());
        if(asignatura.getLaboratorio() > 0){
            if(coordinacionesBusiness.findByAsignaturaAndAnioAndSemestre(asignatura, getAnioSelected(), getSemestreSelected()) == null){
                Coordinacion coordinacion = new Coordinacion();
                coordinacion.setAsignatura(asignatura);
                coordinacion.setAnio(getAnioSelected());
                coordinacion.setSemestre(getSemestreSelected());
                coordinacion.setCantAlumnosEstimado(0);
                coordinacion.setCantAlumnosReal(0);
                coordinacionFacade.create(coordinacion);
            }

            Coordinacion coord = coordinacionesBusiness.findByAsignaturaAndAnioAndSemestre(asignatura, getAnioSelected(), getSemestreSelected());
            List<Seccion> secciones = getSeccionesBusiness().findByAsignaturaAnioYSemestre(asignatura.getId(), getAnioSelected(), getSemestreSelected());
            List<Seccion> seccionesLaboratorio = new ArrayList();
            for(Seccion seccion : secciones){
                if(seccion.getCodigo().equals("L-1") || seccion.getCodigo().equals("L-2") || seccion.getCodigo().equals("L-3") || seccion.getCodigo().equals("L-4") || seccion.getCodigo().equals("L-5") || seccion.getCodigo().equals("L-6") || seccion.getCodigo().equals("L-7") || seccion.getCodigo().equals("L-8") || seccion.getCodigo().equals("L-9") || seccion.getCodigo().equals("L-10")){
                    seccionesLaboratorio.add(seccion);
                }
            }
            switch(seccionesLaboratorio.size()){
                case 0:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-1");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-1 creada con éxito");
                    break;
                }
                case 1:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-2");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-2 creada con éxito");
                    break;                
                }
                case 2:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-3");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-3 creada con éxito");
                    break;                
                }
                case 3:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-4");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-4 creada con éxito");
                    break;                
                }
                case 4:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-5");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-5 creada con éxito");
                    break;                
                }
                case 5:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-6");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-6 creada con éxito");
                    break;                
                }
                case 6:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-7");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-7 creada con éxito");
                    break;                
                }
                case 7:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-8");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-8 creada con éxito");
                    break;                
                }
                case 8:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-9");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-9 creada con éxito");
                    break;                
                }
                case 9:{
                    Seccion seccion = new Seccion();
                    seccion.setCodigo("L-10");
                    seccion.setCoordinacion(coord);
                    getSeccionFacade().create(seccion);
                    JsfUtil.addSuccessMessage("Sección L-10 creada con éxito");
                    break;                
                }
                default:{
                    JsfUtil.addErrorMessage("No se pueden agregar más secciones de laboratorio");
                }
            }    
        }
        else{
            JsfUtil.addErrorMessage("Ésta asignatura no posee horas de laboratorio");
        }
        
        
    }
    
    public void quitarSeccionLaboratorio(){
        Asignatura asignatura = getAsignaturaFacade().find(getAsignaturaSelected());
        List<Seccion> secciones = getSeccionesBusiness().findByAsignaturaAnioYSemestre(asignatura.getId(), getAnioSelected(), getSemestreSelected());
        List<Seccion> seccionesLaboratorio = new ArrayList();
        for(Seccion seccion : secciones){
            if(seccion.getCodigo().equals("L-1") || seccion.getCodigo().equals("L-2") || seccion.getCodigo().equals("L-3") || seccion.getCodigo().equals("L-4") || seccion.getCodigo().equals("L-5") || seccion.getCodigo().equals("L-6") || seccion.getCodigo().equals("L-7") || seccion.getCodigo().equals("L-8") || seccion.getCodigo().equals("L-9") || seccion.getCodigo().equals("L-10")){
                seccionesLaboratorio.add(seccion);
            }
        }
        int last = seccionesLaboratorio.size();
        if(last > 0){
            try{
                getSeccionFacade().remove(seccionesLaboratorio.get(last-1));
                JsfUtil.addSuccessMessage("Última sección de laboratorio borrada con éxito");
            }
            catch(Exception e){
                JsfUtil.addErrorMessage("No se puede eliminar la última sección pues ésta fue asignada");
            }
        }
        else{
            JsfUtil.addErrorMessage("Ésta asignatura no posee secciones de laboratorio");
        }
        
    }
    
    public boolean isSeccionSelected(){
        try{
            if(seccionId != 0L){
                muestraBoton = true;
            }
            else{
                muestraBoton = false;
            }
            return seccionId != 0L;
        }
        catch(Exception e){
        }
        return false;
    }
    
    public String getPrioridad(){
        if(getNivelSelected() % 2 == 0){
            return "tarde";
        }
        else{
            return "mañana";
        }
    }
    
    public List<Profesor> getDisponibilidadProfesores(){
        List<Profesor> profesores = new ArrayList();
        List<String> profesoresAsignatura = checklistBusiness.findProfesorByAsgAnioSemestre(asignaturaSelected, anioSelected, semestreSelected);
        
        for(String id : profesoresAsignatura){
            profesores.add(profesoresBusiness.findByRut(id));
        }
        
        return profesores;
    }
    
    public List<String> getHorariosProfesor(String id){
        List<String> bloques = new ArrayList();
        for(Horario horario : horariosBusiness.findDisponiblesByProfesorId(id)){
            bloques.add(horario.getBloque());
        }
        bloques.sort(null);
        return bloques;
    }    
    
    public String[] getHorariosPlanificados(){
        List<Horario> horarios = horariosBusiness.findByVersionPlanAndSemestreAndAnioAndNivel(versionId, getSemestreSelected(), getAnioSelected(), nivelSelected);        
        String asignaturas[] = new String[108];
        List<String> colores = new ArrayList<>();
        colores.add("#449DED");
        colores.add("#72A603");
        colores.add("#E8541C");
        colores.add("#FFD52F");
        colores.add("#33A3BA");
        colores.add("#FF893B");
        colores.add("#998661");

        Vector v = new Vector();

        for(Horario h : horarios){                        
            String alias = h.getSeccion().getCoordinacion().getAsignatura().getAlias();            
            
            if(alias == null){
                alias = h.getSeccion().getCoordinacion().getAsignatura().getNombre();
            }
            
            String dia = h.getBloque().substring(0, 1);
            int fila = Integer.parseInt(h.getBloque().substring(1, 2)) - 1;
            int columna;
            int posicion;
            int color_posicion;
            
            switch (dia) {
                case "L":  columna = 0;
                         break;
                case "M":  columna = 1;
                         break;
                case "W":  columna = 2;
                         break;
                case "J":  columna = 3;
                         break;
                case "V":  columna = 4;
                         break;
                case "S":  columna = 5;
                         break;               
                default: columna = -1;
                         break;
            }
            
            posicion = fila * 6 + columna;
            
            if(v.indexOf(alias) == -1){
                v.add(alias);                
            }
            
            color_posicion = v.indexOf(alias) % 6;
            asignaturas[posicion + 54] = colores.get(color_posicion);            
            asignaturas[posicion] = alias;
        }

        return asignaturas;
    }
    
    public asignacionHoraria() {
    }    
}
