/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import business.VersionesPlanLocal;
import entities.Asignatura;
import entities.VersionPlan;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.VersionPlanFacadeLocal;

/**
 *
 * @author ariel
 */
@Named(value = "espejosController")
@SessionScoped
public class EspejosController implements Serializable {

    /*      *
     * EJBs *
     *      */
    
    @EJB
    private VersionesPlanLocal versionesPlanBusiness;
    @EJB
    private AsignaturasLocal asignaturasBusiness;
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private VersionPlanFacadeLocal versionPlanFacade;
    /*           *
     * Atributos *
     *           */
    
    private String alias;
    
    private Long[] asignaturasSeleccionadas;
    
    /*         *
     * Métodos *
     *         */ 
    
    public void redirigir() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("/easy-planning-web/faces/coordinador_docente/plan_de_estudios/espejos.xhtml");
    }
    
    public void inicializarAsignaturasSeleccionadas(){
        List<VersionPlan> versiones = versionPlanFacade.findAll();
        long max = 0;
        for (VersionPlan v : versiones){
            if (v.getId() > max)
                max = v.getId();
        }
        System.out.println("max: "+max);
        this.asignaturasSeleccionadas = new Long[Integer.valueOf(max+"")+1];
        if (max > 0){
            System.out.println("super aqui");
            for (int i = 0; i < max+1; i++){
                asignaturasSeleccionadas[i] = 0L;
            }
        }
    }
    
    public void setEspejos(){
        for (long l : this.asignaturasSeleccionadas){
            if (l > 0L){
                Asignatura a = asignaturaFacade.find(l);
                a.setAlias(alias);
                asignaturaFacade.edit(a);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Los espejos han sido seteados correctamente.", null));
    }
    
    public void mostrarSeleccionados(){
        if(asignaturasSeleccionadas.length > 0){
            String salida = "";
            for (int i = 0; i < asignaturasSeleccionadas.length; i++){
                if(asignaturasSeleccionadas[i] != null)
                    salida += asignaturasSeleccionadas[i]+", ";
                else
                    salida +="null, ";
            }
            System.out.println("salida: "+salida);
        }else{
            System.out.println("Salida: null");
        }
    }
    
    public List<VersionPlan> getVersionesPlanActivas(){
        List<VersionPlan> v = this.versionesPlanBusiness.findByPlanificado(true);
        for (VersionPlan vers : v){
            System.out.println(vers.getAnio()+"."+vers.getVersion());
        }
        return v;
    }
    
    public List<VersionPlan> getVersionesCivil(){
        List<VersionPlan> versiones = this.versionesPlanBusiness.findByPlanificado(true);
        List<VersionPlan> civil = new ArrayList<>();
        for (VersionPlan v : versiones){
            if (v.getPlanEstudio().getCarrera().getNombre().equalsIgnoreCase("INGENIERÍA CIVIL EN INFORMÁTICA"))
                civil.add(v);
        }
        return civil;
    }
    
    public List<VersionPlan> getVersionesEjecucion(){
        List<VersionPlan> versiones = this.versionesPlanBusiness.findByPlanificado(true);
        List<VersionPlan> ejecucion = new ArrayList<>();
        for (VersionPlan v : versiones){
            if (!v.getPlanEstudio().getCarrera().getNombre().equalsIgnoreCase("INGENIERÍA CIVIL EN INFORMÁTICA"))
                ejecucion.add(v);
        }
        return ejecucion;
    }
    
    public List<Asignatura> asignaturasByPlan(long idVersion){
        List<Asignatura> asgs = asignaturaFacade.findAll();
        List<Asignatura> salida = new ArrayList<>();
        for (Asignatura a : asgs){
            if (a.getVersionplan().getId() == idVersion)
                salida.add(a);
        }
        return salida;
    }
    
    public String mostrarAsg(int idVersion){
        String salida = "";
        List<Asignatura> asgs = asignaturaFacade.findAll();
        for (Asignatura a : asgs ){
            if (a.getVersionplan().getId() == idVersion)
                salida += a.getNombre()+"<br/>";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("hola mundo"));
        return salida;
    }
    
    /*                   *
     * getters & setters *
     *                   */
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long[] getAsignaturasSeleccionadas() {
        return asignaturasSeleccionadas;
    }

    public void setAsignaturasSeleccionadas(Long[] asignaturasSeleccionadas) {
        this.asignaturasSeleccionadas = asignaturasSeleccionadas;
    }
       
    public EspejosController() {
    }
}
