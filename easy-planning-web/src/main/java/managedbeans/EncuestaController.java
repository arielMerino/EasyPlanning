package managedbeans;

import business.ProfesoresLocal;
import entities.Asignatura;
import entities.Encuesta;
import entities.Checklist;
import entities.ParamSemestreAno;

import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.ParamSemestreAñoFacadeLocal;

/**
 *
 * @author chien
 */
@Named(value = "encuestaController")
@SessionScoped
public class EncuestaController implements Serializable {
    
    @EJB
    private EncuestaFacadeLocal encuestaFacade;
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private ChecklistFacadeLocal checklistFacade;
    @EJB
    private ProfesoresLocal profesorBusiness;
    @EJB
    private ParamSemestreAñoFacadeLocal ejbParam;
    
    private List<String> listaContinuidad;    
    private String comentario;
    private List<Checklist> asignaturas;
    private String[] horariosSeleccionados;
    
    public EncuestaController() {
    }

    public String[] getHorariosSeleccionados() {
        return horariosSeleccionados;
    }

    public void setHorariosSeleccionados(String[] horariosSeleccionados) {
        this.horariosSeleccionados = horariosSeleccionados;
    }

    public List<Checklist> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Checklist> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public EncuestaFacadeLocal getFacade() {
        return encuestaFacade;
    }

    public AsignaturaFacadeLocal getAsignaturaFacade() {
        return asignaturaFacade;
    }
    
    public ChecklistFacadeLocal getChecklistFacade() {
        return checklistFacade;
    }

    public List<String> getListaContinuidad() {
        return listaContinuidad;
    }

    public void setListaContinuidad(List<String> listaContinuidad) {
        this.listaContinuidad = listaContinuidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void crearEncuesta(){

        if(!listaContinuidad.isEmpty()){
            Encuesta encuesta = new Encuesta();                
            getFacade().create(encuesta);

            for(int i = 0; i < listaContinuidad.size(); i++){
                Checklist checklist = new Checklist();
                getChecklistFacade().create(checklist);
                Long id = Long.parseLong(listaContinuidad.get(i));
                Asignatura asignatura = getAsignaturaFacade().find(id);
                checklist.setEncuesta(encuesta);
                checklist.setAsignatura(asignatura);
                getChecklistFacade().edit(checklist);
            }
        }
    }
    
    public ArrayList<Asignatura> getAsignaturasAceptadas(String encuestaId){
        try{
            Long id = Long.parseLong(encuestaId);
            List<Checklist> aux = getFacade().find(id).getListaAsignaturas();

            ArrayList<Asignatura> asignaturas = new ArrayList<>();

            for(Checklist check : aux){
                if(check.isAceptado())            
                    asignaturas.add(check.getAsignatura());
            }

            return asignaturas;
        }catch (Exception e){
            return null;
        }
    }
    
    public void resultadoEncuesta(int id_profesor){
        Long id = Long.parseLong(id_profesor+"");
        ParamSemestreAno semAño = ejbParam.find(Long.parseLong(1+""));
        Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAño(Long.parseLong(id+""), semAño.getSemestreActual(), semAño.getAnoActual());
        for (Checklist asignatura : asignaturas) {
            
        }
    }
    
}
