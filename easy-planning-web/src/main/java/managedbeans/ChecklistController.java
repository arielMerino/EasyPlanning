/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.Asignaturas;
import business.ChecklistsLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import entities.Profesor;
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
import managedbeans.util.JsfUtil;
import managedbeans.util.JsfUtil.PersistAction;
import org.primefaces.context.RequestContext;
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
    
    private String init = "1";
    private Long profesor_id = Long.parseLong(init);
    private Checklist selected;

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

    public Long getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(Long profesor_id) {
        this.profesor_id = profesor_id;
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
        
    /**
     * Creates a new instance of ChecklistController
     */
    public ChecklistController() {
    }
    
    public Checklist findChcecklistByAsignatura(Encuesta encuesta,Long asignatura_id){
        for(Checklist chk : encuesta.getListaAsignaturas()){
            if(chk.getAsignatura().getId()==asignatura_id){
                return chk;
            }
        }
        return null;
    }
    
    public Encuesta encuestaActual(Long profesor_id){
        Encuesta encuesta = new Encuesta();
        List<ParamSemestreAno> param = ejbParam.findAll();
        ParamSemestreAno actual = param.get(param.size()-1);
        for(Encuesta enc : encuestaFacade.findAll()){
            if(actual.getAnoActual()==enc.getAnio() && actual.getSemestreActual()==enc.getSemestre() && Objects.equals(profesor_id, enc.getProfesor().getId())){
                return enc;
            }
        }
        return null;
    }
    
    public void asignaturasParaEncuestas(List<Asignatura> asignaturas,Long id){
        
        Encuesta encuesta = this.encuestaActual(id);        
        
            for(Asignatura asig : asignaturas){
                Checklist check = new Checklist();
                check.setEncuesta(encuesta);
                check.setAsignatura(asig);
                ejbFacade.create(check);
            }
                    
    }
    
    public List<Asignatura> asignaturasSemestrePasado(Long id){
        Encuesta encuesta = this.encuestaActual(id);
        List<Asignatura> asignaturas = checklistBusiness.findAsignaturasByEncuestaId(encuesta.getId());
        
        return asignaturas; 
    }
    
    public void eliminarAsignaturaEncuesta(Long id,List<Asignatura> asignaturas){
        Encuesta encuesta = this.encuestaActual(id);
        /*for(Asignatura asig : asignaturas){
            checklistBusiness.deleteChecklist(encuesta.getId(),asig.getId());
        }*/
        List<Checklist> checklists = encuesta.getListaAsignaturas();
        for(Checklist chk : checklists){
            for(Asignatura asig : asignaturas){
                
                if(asig.getId()==chk.getAsignatura().getId()){
                    this.selected = chk;
                    this.destroy();
                }
            }
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
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProfesorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
        }

    }
    
}
