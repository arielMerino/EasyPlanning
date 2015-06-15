package managedbeans;

import entities.Carrera;
import managedbeans.util.JsfUtil;
import managedbeans.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
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
import sessionbeans.CarreraFacadeLocal;

@Named("carreraController")
@SessionScoped
public class CarreraController implements Serializable {

    @EJB
    private CarreraFacadeLocal ejbFacade;
    private List<Carrera> items = null;
    private Carrera selected;
    private List<String> carreras;

    public CarreraController() {
    }

    public List<Carrera> getAllCarreras(){
        return getFacade().findAll();
    }
    
    public List<String> getCarreras() {
        List<Carrera> lista = ejbFacade.findAll();
        for(Carrera carrera : lista){
            carreras.add(carrera.getNombre());
        }
        return carreras;
    }

    public void setCarreras(List<String> carreras) {
        this.carreras = carreras;
    }

    public Carrera getSelected() {
        return selected;
    }

    public void setSelected(Carrera selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CarreraFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Carrera prepareCreate() {
        selected = new Carrera();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CarreraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CarreraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CarreraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Carrera> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    if(!selected.getNombre().trim().isEmpty()){
                        getFacade().edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                    }
                    else{
                        JsfUtil.addErrorMessage("Nombre de la carrera no debe estar vacio");
                        items = getFacade().findAll();
                    }
                } else {
                    getFacade().remove(selected);
                    JsfUtil.addSuccessMessage(successMessage);
                }                
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage("No se puede realizar tal acción.");
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Carrera getCarrera(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Carrera> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Carrera> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Carrera.class)
    public static class CarreraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CarreraController controller = (CarreraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "carreraController");
            return controller.getCarrera(getKey(value));
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
            if (object instanceof Carrera) {
                Carrera o = (Carrera) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Carrera.class.getName()});
                return null;
            }
        }

    }

}
