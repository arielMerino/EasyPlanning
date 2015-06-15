/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.ChecklistsLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import entities.Asignatura;
import entities.Checklist;
import entities.Encuesta;
import entities.ParamSemestreAno;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import managedbeans.util.JsfUtil;
import managedbeans.util.JsfUtil.PersistAction;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;
import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;

/**
 *
 * @author yerko
 */
@Named(value = "checklistController")
@SessionScoped
public class ChecklistController implements Serializable {

    @EJB
    private ChecklistFacadeLocal ejbFacade;
    @EJB
    private EncuestaFacadeLocal encuestaFacade;
    @EJB
    private ParamSemestreAnioFacadeLocal ejbParam;
    @EJB
    private ChecklistsLocal checklistBusiness;
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private ChecklistsLocal checklistsBusiness;
    
    private String init = "1";
    private Checklist selected;
    private String rutProfesor = "";

    public Checklist getSelected() {
        return selected;
    }

    public void setSelected(Checklist selected) {
        this.selected = selected;
    }

    public AsignaturaFacadeLocal getAsignaturaFacade() {
        return asignaturaFacade;
    }

    public ChecklistsLocal getChecklistBusiness() {
        return checklistBusiness;
    }

    public void setChecklistBusiness(ChecklistsLocal checklistBusiness) {
        this.checklistBusiness = checklistBusiness;
    }

    public ChecklistFacadeLocal getEjbFacade() {
        return ejbFacade;
    }

    public EncuestaFacadeLocal getEncuestaFacade() {
        return encuestaFacade;
    }

    public ParamSemestreAnioFacadeLocal getEjbParam() {
        return ejbParam;
    }
    
    public ChecklistsLocal getChecklistsBusiness() {
        return checklistsBusiness;
    }    

    public String getRutProfesor() {
        return rutProfesor;
    }

    public void setRutProfesor(String rutProfesor) {
        System.out.println("CHECKLIST RUT PROFE: " + rutProfesor);
        this.rutProfesor = rutProfesor;
    }
        
    /**
     * Creates a new instance of ChecklistController
     */
    public ChecklistController() {
    }
    
    public Checklist findChcecklistByAsignatura(Encuesta encuesta,Long asignatura_id){
        List<Checklist> checklists = getChecklistsBusiness().findChecklistByIdEncuesta(encuesta.getId());
        for(Checklist chk : checklists){
            if(Objects.equals(chk.getAsignatura().getId(), asignatura_id)){
                return chk;
            }
        }
        return null;
    }
    
    public Encuesta encuestaActual(String rutProfesor){
        Encuesta encuesta = new Encuesta();
        List<ParamSemestreAno> param = ejbParam.findAll();
        ParamSemestreAno actual = param.get(param.size()-1);
        for(Encuesta enc : encuestaFacade.findAll()){
            if(actual.getAnoActual()==enc.getAnio() && actual.getSemestreActual()==enc.getSemestre() && rutProfesor.equals(enc.getProfesor().getRutProfesor())){
                return enc;
            }
        }
        return null;
    } 
    
    public void asignaturasParaEncuestas(List<Asignatura> asignaturas,String rutProfesor){
        
        Encuesta encuesta = this.encuestaActual(rutProfesor);   
        List<Checklist> checklists = getChecklistsBusiness().findChecklistByIdEncuesta(encuesta.getId());
        
        boolean ver=true;
        FacesContext context = FacesContext.getCurrentInstance();
        
        for(Checklist chk : checklists){
            for(Asignatura asig : asignaturas){
                System.out.println("Asignatura: "+asig.getId().toString()+", Check: "+chk.getAsignatura().getId().toString());
                if(asig.getId()==chk.getAsignatura().getId()){
                    ver=false;
                }
            }
        }
        
        
        if(ver){
            for(Asignatura asig : asignaturas){
                Checklist check = new Checklist();
                check.setEncuesta(encuesta);
                check.setAsignatura(asig);
                ejbFacade.create(check);
            }
            if(asignaturas.size() > 0){
                context.addMessage(null, new FacesMessage("Resultado",  "Se han incluido las asignaturas exitósamente.") );
            }
            else{
                context.addMessage(null, new FacesMessage("Resultado",  "No se ha seleccionado ninguna asignatura para agregar.") );
            }
        }
        else{
            context.addMessage(null, new FacesMessage("Advertencia",  "Una asignatura seleccionada ya esta incluida en la enceusta actual.") );
        }
                    
    }
    
    public List<Asignatura> asignaturasSemestrePasado(String rutProfesor){
        try{
            Encuesta encuesta = this.encuestaActual(rutProfesor);
            List<Asignatura> asignaturas = checklistBusiness.findAsignaturasByEncuestaId(encuesta.getId());

            return asignaturas;
        } 
        catch(Exception e){
            return new ArrayList();
        }
    }
    
    public void eliminarAsignaturaEncuesta(String rutProfesor,List<Asignatura> asignaturas){
        Encuesta encuesta = this.encuestaActual(rutProfesor);
        /*for(Asignatura asig : asignaturas){
            checklistBusiness.deleteChecklist(encuesta.getId(),asig.getId());
        }*/
        FacesContext context = FacesContext.getCurrentInstance();
        int contadorEliminados = 0;
        List<Checklist> checklists = getChecklistsBusiness().findChecklistByIdEncuesta(encuesta.getId());
        for(Checklist chk : checklists){
                for(Asignatura asig : asignaturas){

                    if(asig.getId()==chk.getAsignatura().getId()){
                        this.selected = chk;
                        this.destroy();
                        contadorEliminados++;
                        /*context.addMessage(null, new FacesMessage("Resultado",  "Se han sacado las asignaturas exitósamente.") );*/
                    }
                }
            }
        if (contadorEliminados == 0){
            context.addMessage(null, new FacesMessage("Resultado",  "No se han seleccionado asignaturas para quitar.") );
        }
    }
    
    protected void setEmbeddableKeys() {
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getEjbFacade().edit(selected);
                } else {
                    getEjbFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ChecklistDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
        }

    }
    
}
