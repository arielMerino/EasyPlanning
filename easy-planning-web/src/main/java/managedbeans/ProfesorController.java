package managedbeans;

import entities.Horario;
import entities.Profesor;
import entities.Asignatura;
import entities.Encuesta;
import managedbeans.util.JsfUtil;
import managedbeans.util.JsfUtil.PersistAction;
import sessionbeans.ProfesorFacadeLocal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.EncuestaFacadeLocal;

@Named("profesorController")
@SessionScoped
public class ProfesorController implements Serializable {

    @EJB
    private ProfesorFacadeLocal ejbFacade;
    @EJB
    private HorarioFacadeLocal horarioFacade;
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private EncuestaFacadeLocal encuestaFacade;
    private List<Profesor> items = null;
    private Profesor selected;
    private String[] horariosSeleccionados;

    public ProfesorController() {
    }

    public String[] getHorariosSeleccionados() {
        return horariosSeleccionados;
    }

    public void setHorariosSeleccionados(String[] horariosSeleccionados) {
        this.horariosSeleccionados = horariosSeleccionados;
    }

    public Profesor getSelected() {
        return selected;
    }

    public void setSelected(Profesor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProfesorFacadeLocal getFacade() {
        return ejbFacade;
    }    

    public HorarioFacadeLocal getHorarioFacade() {
        return horarioFacade;
    }

    public void setHorarioFacade(HorarioFacadeLocal horarioFacade) {
        this.horarioFacade = horarioFacade;
    }

    public Profesor prepareCreate() {
        selected = new Profesor();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProfesorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProfesorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProfesorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Profesor> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public void editProfesor(Profesor profesor){
        selected = profesor;
    }
    
    public void deleteProfesor(Profesor profesor){
        selected = profesor;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public Profesor getProfesor(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Profesor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Profesor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /*
        Función que retorna una lista con los bloques horarios disponibles de un profesor
    */
    public ArrayList<Horario> getDisponibles(long profesorId){
        List<Horario> horarios = this.horarioFacade.findAll();
        ArrayList<Horario> disponibles = new ArrayList<>();
        for (int i = 0; i < horarios.size(); i++) {
            if (profesorId == horarios.get(i).getProfesor().getId()) {
                if (horarios.get(i).getSeccion()==null) {
                    disponibles.add(horarios.get(i));
                }
            }
        }
        return disponibles;
    }
    
    public ArrayList<Asignatura> asignaturasProfesor(String profesorid){
        Long id = Long.parseLong(profesorid);
        List<Asignatura> aux = getFacade().find(id).getAsignaturas();
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        for(Asignatura asig : aux){
            asignaturas.add(asig);
        }
        return asignaturas;
    }
    
    public String comentarioEncuestaProfesor(Long profesorid){
        List<Encuesta> encuestas = encuestaFacade.findAll();
        Encuesta encuesta = new Encuesta();
        for(Encuesta e : encuestas){
            if(Objects.equals(e.getProfesor().getId(), profesorid)){
                encuesta = e;
            }
        }
        return encuesta.getComentario();
    }

    @FacesConverter(forClass = Profesor.class)
    public static class ProfesorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfesorController controller = (ProfesorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "profesorController");
            return controller.getProfesor(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Profesor) {
                Profesor o = (Profesor) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Profesor.class.getName()});
                return null;
            }
        }

    }

}
