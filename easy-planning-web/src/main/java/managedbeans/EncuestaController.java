package managedbeans;

import business.ChecklistsLocal;
import business.HorariosLocal;
import business.ProfesoresLocal;
import entities.Asignatura;
import entities.Encuesta;
import entities.Checklist;
import entities.Horario;
import entities.ParamSemestreAno;

import sessionbeans.EncuestaFacadeLocal;
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
    @EJB
    private ChecklistsLocal checklistsBusiness;
    
    private List<String> listaContinuidad;    
    private String comentario = "";
    private Long[] asignaturas;
    private String[] horariosSeleccionados;    
    
    public EncuestaController() {
    }        

    public ParamSemestreAnioFacadeLocal getParamFacade() {
        return paramFacade;
    }

    public void setParamFacade(ParamSemestreAnioFacadeLocal paramFacade) {
        this.paramFacade = paramFacade;
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
    
    public ChecklistsLocal getChecklistsBusiness() {
        return checklistsBusiness;
    }
    
    public ArrayList<Asignatura> getAsignaturasAceptadas(String encuestaId){
        try{
            Long id = Long.parseLong(encuestaId);
            List<Checklist> aux = getChecklistsBusiness().findChecklistByIdEncuesta(id);

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
    
    public List<Asignatura> getAsignaturasRechazadas(String encuestaId){
        try{
            Long id = Long.parseLong(encuestaId);
            List<Checklist> aux = getChecklistsBusiness().findChecklistByIdEncuesta(id);
            List<Asignatura> rechazadas = new ArrayList();
            
            for(Checklist check : aux){
                if(!check.isAceptado()){
                    rechazadas.add(check.getAsignatura());
                }
            }
            return rechazadas;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public void resultadoEncuesta(String rutProfesor){
        ParamSemestreAno semAnio = paramFacade.find(Long.parseLong(1+""));
        try{
            Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semAnio.getSemestreActual(), semAnio.getAnoActual());
            encuesta.setComentario(comentario);
            encuestaFacade.edit(encuesta);
            
            for (Long asignatura : asignaturas) {
                Checklist check = checklistFacade.find(asignatura);
                check.setAceptado(true);
                checklistFacade.edit(check);
                
            }
            setFalseChecklist(rutProfesor);
            for(String bloque : horariosSeleccionados){
                if(horarioBusiness.findByBloqueAndProfesor(bloque, rutProfesor) == null){                    
                    Horario horario = new Horario();
                    horario.setBloque(bloque);
                    horario.setProfesor(profesorFacade.find(rutProfesor));
                    horarioFacade.create(horario);
                }
            }
            dropHorariosNoSeleccionados(rutProfesor);
            JsfUtil.addSuccessMessage("Encuesta registrada con éxito");
            
        }
        catch(Exception e){
            JsfUtil.addErrorMessage("Ha ocurrido un error");
        }
        
    }
    
    public void setFalseChecklist(String rutProfesor){
        ParamSemestreAno semAnio = paramFacade.find(Long.parseLong(1+""));
        Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semAnio.getSemestreActual(), semAnio.getAnoActual());
        List<Checklist> lista = getChecklistsBusiness().findChecklistByIdEncuesta(encuesta.getId());
        List<Checklist> noSeleccionadas = new ArrayList();
        boolean flag;        
        for(Checklist todas : lista){
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
    
    public boolean hayEncuestaContestado(String rutProfesor, int semestre, int anio){
        try{
            Encuesta e = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semestre, anio);
            List<Checklist> c = e.getListaAsignaturas();
            System.out.println(c.size());
            for(Checklist check : c){
                if(check.isAceptado())            
                    return true;
            }
            
            List<Horario> h = horarioBusiness.findDisponiblesByProfesorId(rutProfesor);
            
            System.out.println(!h.isEmpty());
            
            return !h.isEmpty();
        }catch (Exception e){
            return false;
        }
    }
    
    public boolean hayEncuesta(String rutProfesor){
        ParamSemestreAno semAnio = paramFacade.find(Long.parseLong(1+""));
        System.out.println("id_profesor: "+rutProfesor);
        try{
            Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semAnio.getSemestreActual(), semAnio.getAnoActual());
            if(encuesta != null){
                System.out.println("hayEncuesta retorna true, id profesor: "+rutProfesor);
                return true;
            }
            else{
                System.out.println("hayEncuesta encuesta igual a null, retorna false, id profesor: "+rutProfesor);
                return false;
            }
        }
        catch(Exception e){
            System.out.println("EncuestaController: retorna false, id profesor: "+rutProfesor);
            return false;
        }
    }
    
    public Encuesta getEncuestaContestado(String rutProfesor, int semestre, int anio){
        try{
            System.out.println(semestre+ " " + anio);
            Encuesta e = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semestre, anio);
            return e;
        }catch (Exception e){
            return null;
        }        
    }    
    
    public void dropHorariosNoSeleccionados(String rutProfesor){
        List<Horario> horarios = horarioBusiness.findBySeleccionados(rutProfesor);
        List<Horario> porBorrar = new ArrayList();
        
        if(horariosSeleccionados.length > 0){
            for(Horario horarioGuardado : horarios){
                boolean flag = false;
                for(String horario : horariosSeleccionados){
                    if(horario.equals(horarioGuardado.getBloque())){
                        //System.out.println("EncuestaController: se compara "+horario+" con "+horarioGuardado.getBloque());
                        flag = true;
                    }
                }
                if(!flag){
                    //System.out.println("EncuestaController: se agrega el horario "+horarioGuardado.getBloque());
                    porBorrar.add(horarioGuardado);
                }
            }
        }
        for(Horario drop : porBorrar){
            //Horario dropHorario = horarioBusiness.findByBloqueAndProfesor(drop, id_profesor);
            horarioFacade.remove(drop);
        }
    }
}
