package managedbeans;

import business.ProfesoresLocal;
import entities.Asignatura;
import entities.Encuesta;
import entities.Checklist;
import entities.Horario;
import entities.ParamSemestreAno;
import java.io.IOException;

import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import managedbeans.util.JsfUtil;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.ParamSemestreAñoFacadeLocal;
import sessionbeans.ProfesorFacadeLocal;

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
    private ProfesorFacadeLocal ejbProfesor;
    @EJB
    private ParamSemestreAñoFacadeLocal ejbParam;
    @EJB
    private HorarioFacadeLocal ejbHorario;
    
    private List<String> listaContinuidad;    
    private String comentario = "";
    private Long[] asignaturas;
    private String[] horariosSeleccionados;
    
    public EncuestaController() {
    }

    public String[] getHorariosSeleccionados() {
        return horariosSeleccionados;
    }

    public void setHorariosSeleccionados(String[] horariosSeleccionados) {
        this.horariosSeleccionados = horariosSeleccionados;
    }

    public Long[] getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Long[] asignaturas) {
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
        //Long id = id_profesor;
        ParamSemestreAno semAño = ejbParam.find(Long.parseLong(1+""));
        try{
            Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAño(id, semAño.getSemestreActual(), semAño.getAnoActual());
            encuesta.setComentario(comentario);
            encuestaFacade.edit(encuesta);
        }
        catch(NullPointerException e){
            JsfUtil.addErrorMessage("Ha ocurrido un error");
        }
        try{
            for (Long asignatura : asignaturas) {
                Checklist check = checklistFacade.find(asignatura);
                check.setAceptado(true);
                checklistFacade.edit(check);
                
            }
            for(String bloque : horariosSeleccionados){
                Horario horario = new Horario();
                horario.setBloque(bloque);
                horario.setProfesor(ejbProfesor.find(id));
                ejbHorario.create(horario);
            }
            JsfUtil.addSuccessMessage("Encuesta registrada con éxito");
            
        }
        catch(Exception e){
            JsfUtil.addErrorMessage("Ha ocurrido un error");
        }
        
    }
    
}
