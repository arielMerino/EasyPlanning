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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

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
    /*           *
     * Atributos *
     *           */
    
    private String alias;
    
    private List<Long> asignaturasSeleccionadas;
    
    private long idqlo;
    /*         *
     * Métodos *
     *         */ 
    
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
            if (v.getPlanEstudio().getCarrera().getNombre().equalsIgnoreCase("INGENIERÍA CIVIL INFORMÁTICA"))
                civil.add(v);
        }
        return civil;
    }
    
    public List<VersionPlan> getVersioneseEjecucion(){
        List<VersionPlan> versiones = this.versionesPlanBusiness.findByPlanificado(true);
        List<VersionPlan> ejecucion = new ArrayList<>();
        for (VersionPlan v : versiones){
            if (!v.getPlanEstudio().getCarrera().getNombre().equalsIgnoreCase("INGENIERÍA CIVIL INFORMÁTICA"))
                ejecucion.add(v);
        }
        return ejecucion;
    }
    
    public List<Asignatura> asignaturasByPlan(long idVersion){
        return asignaturasBusiness.findByCarreraAndPlan(idVersion);
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

    public List<Long> getAsignaturasSeleccionadas() {
        return asignaturasSeleccionadas;
    }

    public void setAsignaturasSeleccionadas(List<Long> asignaturasSeleccionadas) {
        this.asignaturasSeleccionadas = asignaturasSeleccionadas;
    }

    public long getIdqlo() {
        return idqlo;
    }

    public void setIdqlo(long idqlo) {
        this.idqlo = idqlo;
    }
    
    public EspejosController() {
    }
}
