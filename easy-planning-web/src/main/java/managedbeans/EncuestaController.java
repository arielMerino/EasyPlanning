package managedbeans;

import business.HorariosLocal;
import business.ProfesoresLocal;
import entities.Asignatura;
import entities.Encuesta;
import entities.Checklist;
import entities.Horario;
import entities.ParamSemestreAno;

import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import managedbeans.util.JsfUtil;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;
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
    private ProfesorFacadeLocal profesorFacade;
    @EJB
    private ParamSemestreAnioFacadeLocal paramFacade;
    @EJB
    private HorarioFacadeLocal horarioFacade;
    @EJB
    private HorariosLocal horarioBusiness;
    
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
            encuestaFacade.create(encuesta);

            for(int i = 0; i < listaContinuidad.size(); i++){
                Checklist checklist = new Checklist();
                checklistFacade.create(checklist);
                Long id = Long.parseLong(listaContinuidad.get(i));
                Asignatura asignatura = asignaturaFacade.find(id);
                checklist.setEncuesta(encuesta);
                checklist.setAsignatura(asignatura);
                checklistFacade.edit(checklist);
            }
        }
    }
    
    public ArrayList<Asignatura> getAsignaturasAceptadas(String encuestaId){
        try{
            Long id = Long.parseLong(encuestaId);
            List<Checklist> aux = encuestaFacade.find(id).getListaAsignaturas();

            ArrayList<Asignatura> lista_a = new ArrayList<>();

            for(Checklist check : aux){
                if(check.isAceptado())            
                    lista_a.add(check.getAsignatura());
            }

            return lista_a;
        }catch (Exception e){
            return null;
        }
    }
    
    public void resultadoEncuesta(int id_profesor){
        Long id = Long.parseLong(id_profesor+"");
        ParamSemestreAno semAnio = paramFacade.find(Long.parseLong(1+""));
        try{
            Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(id, semAnio.getSemestreActual(), semAnio.getAnoActual());
            encuesta.setComentario(comentario);
            encuestaFacade.edit(encuesta);
            
            for (Long asignatura : asignaturas) {
                Checklist check = checklistFacade.find(asignatura);
                check.setAceptado(true);
                checklistFacade.edit(check);
                
            }
            setFalseChecklist(id);
            for(String bloque : horariosSeleccionados){
                if(horarioBusiness.findByBloqueAndProfesor(bloque, id) == null){                    
                    Horario horario = new Horario();
                    horario.setBloque(bloque);
                    horario.setProfesor(profesorFacade.find(id));
                    horarioFacade.create(horario);
                }
            }
            JsfUtil.addSuccessMessage("Encuesta registrada con éxito");
            
        }
        catch(Exception e){
            JsfUtil.addErrorMessage("Ha ocurrido un error");
        }
        
    }
    
    public void setFalseChecklist(Long id_profesor){
        ParamSemestreAno semAnio = paramFacade.find(Long.parseLong(1+""));
        Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(id_profesor, semAnio.getSemestreActual(), semAnio.getAnoActual());
        List<Checklist> noSeleccionadas = new ArrayList();
        boolean flag;        
        for(Checklist todas : encuesta.getListaAsignaturas()){
            flag = false;
            for(Long seleccionada : asignaturas){
                if(Objects.equals(seleccionada, todas.getId())){
                    flag = true;
                }
            }
            if(!flag){
                noSeleccionadas.add(todas);
            }
        }
        for(Checklist noSelect : noSeleccionadas){
            noSelect.setAceptado(false);
            checklistFacade.edit(noSelect);
        }
    }
    
    public boolean hayEncuestaContestado(String profesorId, int semestre, int anio){
        try{
            Long id = Long.parseLong(profesorId);
            Encuesta e = profesorBusiness.getEncuestaBySemestreAndAnio(id, semestre, anio);
            List<Checklist> c = e.getListaAsignaturas();

            for(Checklist check : c){
                if(check.isAceptado())            
                    return true;
            }
            
            List<Horario> h = horarioBusiness.findDisponiblesByProfesorId(id);
            
            for(Horario horario : h){
                return true;
            }
            
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
