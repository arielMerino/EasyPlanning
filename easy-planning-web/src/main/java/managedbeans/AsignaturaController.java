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
import java.io.IOException;
import entities.VersionPlan;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;
import sessionbeans.SeccionFacadeLocal;
import sessionbeans.VersionPlanFacadeLocal;

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
    @EJB
    private VersionPlanFacadeLocal versionFacade;
    
    private List<Asignatura> items = null;
    private Asignatura selected;
    private Asignatura asignatura;
    private int planEstudios = 0;
    private int nivel = 0;
    private List<Asignatura> asignaturasFiltradas;
    private List<Asignatura> asignaturaEncuestas;
    private List<Asignatura> asignaturasSemestrePasado;

    public List<Asignatura> getAsignaturasSemestrePasado() {
        return asignaturasSemestrePasado;
    }

    public void setAsignaturasSemestrePasado(List<Asignatura> asignaturasSemestrePasado) {
        this.asignaturasSemestrePasado = asignaturasSemestrePasado;
    }

    public List<Asignatura> getAsignaturaEncuestas() {
        return asignaturaEncuestas;
    }

    public void setAsignaturaEncuestas(List<Asignatura> asignaturaEncuestas) {
        this.asignaturaEncuestas = asignaturaEncuestas;
    }
    
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

    public int getPlanEstudios() {
        return planEstudios;
    }

    public void setPlanEstudios(int planEstudios) {
        this.planEstudios = planEstudios;
    }
    
    
    public AsignaturaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Asignatura> getItems() {
        return ejbFacade.findAll();
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
    
    public Asignatura getAsignatura(){
        return asignatura;
    }
    
    public void setAsignatura(String id){
        Long id_asignatura = Long.parseLong(id+"");
        this.asignatura = this.ejbFacade.find(id_asignatura);
    }
    /*
    carga la lista de asignaturas en items
    */
    public boolean getAsignaturas(){
        try {
            this.items = getFacade().findAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
    
    public String getDatosPlan(VersionPlan version){
        if(version != null){
            String salida = "";
            salida += version.getPlanEstudio().getCodigo();
            salida += " - " + version.getAnio();
            salida += "." + version.getVersion();
            int j;
            if((j = version.getPlanEstudio().getJornada()) == 0)
                salida += " (Diurno)";
            else
                salida += " (Vespertino)";
            return salida;
        }
        return "";
    }
            
    
    
    public Integer bloquesTotales(Long asignaturaId){
        Integer teoria = getFacade().find(asignaturaId).getTeoria();
        Integer ejercicios = getFacade().find(asignaturaId).getEjercicios();
        Integer lab = getFacade().find(asignaturaId).getLaboratorio();
        Integer totalHoras = teoria+ejercicios+lab;
        return totalHoras;
    }
    
    public ArrayList<Asignatura> getAsignaturasByPlan(int plan, int nivel){
        Long lplan = Long.valueOf(plan + "");
        ArrayList<Asignatura> asignaturasByPlan = new ArrayList<>();
        List<Asignatura> asignaturas = getListaAsignaturas();
        if (plan == 0) {
            for (Asignatura asignatura : asignaturas) {
                asignaturasByPlan.add(asignatura);
            }
            if (nivel != 0) {
                asignaturasByPlan = getAsignaturasPorNivel(asignaturasByPlan, nivel);
            }
            return asignaturasByPlan;
        }
        for (Asignatura asg : asignaturas) {
            if (Objects.equals(asg.getVersionplan().getId(), lplan) ) {
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
    
    //retorna una lista de enteros con los id de todos los planes de estudios
    public ArrayList<Integer> getPlanesDeEstudio(){
        ArrayList<Integer> planesEstudio = new ArrayList<>();
        List<Asignatura> asignaturas = getFacade().findAll();
        for (Asignatura asg : asignaturas){
            if(!planesEstudio.contains(Integer.valueOf(asg.getVersionplan().getId()+""))){
                planesEstudio.add(Integer.valueOf(asg.getVersionplan().getId()+""));
            }
        }
        return planesEstudio;
    }
    
    public int contarVersionesNoVacias(long idVersionPlan){
        try{
            int contador = 0;
            List<Asignatura> asignaturas = ejbFacade.findAll();
            for ( Asignatura a : asignaturas ){
                if(a.getVersionplan().getId().equals(idVersionPlan))
                    contador++;
            }
            return contador;
        }catch(Exception e){
            System.out.println("hubo algun error");
            return 0;
        }
    }
    
    public boolean haveAsignaturas(VersionPlan vers){
        try{
            List<Asignatura> asignaturas = ejbFacade.findAll();
            for (Asignatura a : asignaturas){
                if (a.getVersionplan().getId() == vers.getId()){
                    System.out.println(vers.getAnio()+"-"+vers.getVersion());
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    
    //retorna una lista de enteros con los id de todos los planes de estudios correspondientes a una carrera dada
    public ArrayList<VersionPlan> getPlanesDeEstudio(int carreraId){
        ArrayList<VersionPlan> planesEstudio = new ArrayList<>();
        List<VersionPlan> versiones = versionFacade.findAll();
        for (VersionPlan v : versiones){
            if (!planesEstudio.contains(v) && v.getPlanEstudio().getCarrera().getId().toString().equals(carreraId+"") && v.isPlanificado() && haveAsignaturas(v))
                planesEstudio.add(v);
        }
        return planesEstudio;
    }
    
    public ArrayList<VersionPlan> getAllPlanesDeEstudio(int carreraId){
        ArrayList<VersionPlan> planesEstudio = new ArrayList<>();
        List<VersionPlan> versiones = versionFacade.findAll();
        for (VersionPlan v : versiones){
            if (!planesEstudio.contains(v) && v.getPlanEstudio().getCarrera().getId().toString().equals(carreraId+""))
                planesEstudio.add(v);
        }
        return planesEstudio;
    }
    
    public List<VersionPlan> getPlanesEstudio(){
        return versionFacade.findAll();
    }
    
    public ArrayList<Integer> getMaxNivel(){
        int nivel = 0;
        ArrayList<Integer> niveles = new ArrayList<>();
        List<Asignatura> asignaturas = getListaAsignaturas();
        if(getPlanEstudios() == 0){
            for( Asignatura asg : asignaturas){
                if(asg.getNivel() > nivel){
                    nivel = asg.getNivel();
                }
            }
        }
        for( Asignatura asg : asignaturas){
            if(asg.getNivel() > nivel && asg.getVersionplan().getId().equals(getPlanEstudios())){
                nivel = asg.getNivel();
            }
        }
        for (int i = 0; i < nivel; i++) {
            niveles.add(i+1);
        }
        return niveles;
    }
    
    public List<Asignatura> findByAsignaturaAsignada(String rutProfesor){
        ParamSemestreAno semAnio = ejbParam.find(Long.parseLong(1+""));
        return asignaturaBusiness.findByAsignaturaAsignada(rutProfesor, semAnio.getAnoActual(), semAnio.getSemestreActual());
    }
    
    private boolean aliasValido(String alias){
        String trim = alias.trim();
        if (trim.length() > 0){
            if (trim.length() > 10){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El alias debe contener un máximo de 10 caracteres."));
                return false;
            }
            else{
                return true;
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El alias no debe estar vacío."));
            return false;
        }
    }
    
    public void guardarAliasAsignatura() throws IOException{                
        if(aliasValido(asignatura.getAlias())){
            getFacade().edit(asignatura);
            getAsignaturas();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/easy-planning-web/faces/coordinador_docente/asignaturas/listar_asignaturas.xhtml");
        }
    }
    
    public boolean tieneAlias(String alias){
        System.out.println("Asignatura Controller - alias: "+alias);
        if(alias.equals("")){
            System.out.println("Asignatura Controller - tieneAlias: false");
            return false;
        }
        else{
            System.out.println("Asignatura Controller - tieneAlias: true");
            return true;
        }
    }

}
