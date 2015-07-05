package managedbeans;

import business.ChecklistsLocal;
import business.HorariosLocal;
import business.ProfesoresLocal;
//import com.sun.javafx.scene.control.skin.VirtualFlow;
import entities.Asignatura;
import entities.Encuesta;
import entities.Checklist;
import entities.Horario;
import entities.ParamSemestreAno;
import java.io.IOException;

import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.ChecklistFacadeLocal;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import managedbeans.util.JsfUtil;
import sessionbeans.HorarioFacadeLocal;
import sessionbeans.ParamSemestreAnioFacadeLocal;
import sessionbeans.ProfesorFacadeLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chien
 */

@Named(value = "encuestaController")
@SessionScoped
public class EncuestaController implements Serializable {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    
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
    private List<String> noBorrables;
    
    public EncuestaController() {
    }

    public EncuestaFacadeLocal getEncuestaFacade() {
        return encuestaFacade;
    }

    public void setEncuestaFacade(EncuestaFacadeLocal encuestaFacade) {
        this.encuestaFacade = encuestaFacade;
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

    public List<String> getNoBorrables() {
        return noBorrables;
    }

    public void setNoBorrables(List<String> noBorrables) {
        this.noBorrables = noBorrables;
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
    
    public void crearEncuesta(String rut){
        int semestreAnterior;
        int anioAnterior;
        
        ParamSemestreAno actual = new ParamSemestreAno();
        actual = paramFacade.findAll().get(0);
        Encuesta encuesta = new Encuesta();
        encuesta.setAnio(actual.getAnoActual());
        encuesta.setSemestre(actual.getSemestreActual());
        encuesta.setProfesor(profesorFacade.find(rut));
        encuestaFacade.create(encuesta);
        
        if(actual.getSemestreActual()==1){
            semestreAnterior = 2;
            anioAnterior = actual.getAnoActual()-1;
        }
        else{
            semestreAnterior = 1;
            anioAnterior = actual.getAnoActual();
        }

        for(Encuesta e : encuestaFacade.findAll()){
            if(e.getProfesor().getRutProfesor().equals(rut) && e.getAnio()==anioAnterior && e.getSemestre()==semestreAnterior){
                for(Checklist c : e.getListaAsignaturas()){
                    if(c.isAceptado()){
                        Checklist check = new Checklist();
                        check.setAceptado(false);
                        check.setEncuesta(encuesta);
                        check.setAsignatura(c.getAsignatura());
                        checklistFacade.create(check);
                    }
                }
            }
        }
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
    
    public void precargar(String rutProfesor){
        List<Horario> horarios = horarioBusiness.findDisponiblesByProfesorId(rutProfesor);
        List<String> noBorrar = new ArrayList<>();
        String[] bloques = new String[horarios.size()];
        for (int i = 0; i < horarios.size(); i++){
            bloques[i] = horarios.get(i).getBloque();
        }
        
        if (bloques.length > 0) {
            for (String bloque : bloques) {
                System.out.printf("es borrable: "+bloque+" ");
                if (!isBorrable(bloque, rutProfesor)) {
                    System.out.printf("false\n");
                    noBorrar.add(bloque);
                }else{
                    System.out.printf("true\n");
                }
            }
        }
        setHorariosSeleccionados(bloques);
        setNoBorrables(noBorrar);
    }
    
    public void precargarAsignaturas(String rutProfesor){
        ParamSemestreAno semAnio = paramFacade.find(1L);
        Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semAnio.getSemestreActual(), semAnio.getAnoActual());
        if ( encuesta != null){
            List<Checklist> checklists = checklistsBusiness.findChecklistByIdEncuesta(encuesta.getId());
            if (checklists != null){
                List<Checklist> aceptados = new ArrayList<>();
                for (Checklist c : checklists){
                    if(c.isAceptado())
                       aceptados.add(c);
                }
                if(aceptados.size() > 0){
                    Long[] asgs = new Long[aceptados.size()];
                    for (int i = 0; i < aceptados.size(); i++){
                        asgs[i] = aceptados.get(i).getId();
                    }
                    setAsignaturas(asgs);
                }
            }
        }
    }
    
    public void redirigir() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("/easy-planning-web/faces/profesor/encuesta.xhtml");
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
    
    public boolean contenido(String[] lista, String str){
        if(lista == null){
            System.out.println("por que es null?");
            return false;
        }
        for (String s : lista) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isDesSeleccionable(String bloque, String rutProfesor){
        if (contenido (horariosSeleccionados, bloque)){
            if (!isBorrable(bloque, rutProfesor))
                return false;
        }
        return true;
    }
    
    public List<Horario> obtenerHorariosAsignadosPorBloqueYProfesor(String bloque, String rutProfesor){
        List<Horario> horarios = horarioBusiness.findAll();
        List<Horario> salida = new ArrayList<>();
        for(Horario h : horarios){
            if(h.getSeccion() != null){
                if(h.getProfesor() != null){
                    if(h.getBloque().equals(bloque) && h.getProfesor().getRutProfesor().equals(rutProfesor))
                        salida.add(h);
                }
            }
        }
        return salida;
    }
    
    public boolean isBorrable(String bloque, String rutProfesor){
        ParamSemestreAno p = paramFacade.find(1L);
        List<Horario> horarios = obtenerHorariosAsignadosPorBloqueYProfesor(bloque, rutProfesor);
        System.out.println("cantidad de horarios : "+horarios.size());
        for (Horario h : horarios){
            if (h.getSeccion().getCoordinacion().getAnio()==p.getAnoActual() && h.getSeccion().getCoordinacion().getSemestre()==p.getSemestreActual())
                return false;
        }
        return true;
    }
    
    public void dropHorarios(String rutProfesor){
        List<Horario> horarios = horarioBusiness.findBySeleccionados(rutProfesor);
        if (horarios != null){
            for (Horario h : horarios){
                if (h.getSeccion()==null){
                    if (isBorrable(h.getBloque(), rutProfesor))
                        horarioFacade.remove(h);
                }
            }
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
            dropHorarios(rutProfesor);
            //inicio recuperacion
            
            List<String> aux = new ArrayList<>();
            aux.addAll(Arrays.asList(horariosSeleccionados));
            for(String bloque : noBorrables){
                if (!aux.contains(bloque))
                    aux.add(bloque);
            }
            horariosSeleccionados = new String[aux.size()];
            if (aux.size() > 0){
                for (int i = 0; i < aux.size(); i++){
                    horariosSeleccionados[i] = aux.get(i);
                }
            }
            //fin recuperacion
            for(String bloque : horariosSeleccionados){
                //if(horarioBusiness.findByBloqueAndProfesor(bloque, rutProfesor) == null){                    
                Horario horario = new Horario();
                horario.setBloque(bloque);
                horario.setProfesor(profesorFacade.find(rutProfesor));
                horarioFacade.create(horario);
                //}
            }
            //dropHorariosNoSeleccionados(rutProfesor);
            JsfUtil.addSuccessMessage("Encuesta registrada con éxito");
            
        }
        catch(Exception e){
            logger.debug("Ha ocurrido un error", e);
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
        
    public Encuesta getEncuesta(String rutProfesor, int semestre, int anio){
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
    
    public Encuesta getEncuestaProfesor(String rut){
        ParamSemestreAno param = paramFacade.find(1L);
        return encuestaFacade.findByProfesor(rut, param.getAnoActual(), param.getSemestreActual());
    }
}
