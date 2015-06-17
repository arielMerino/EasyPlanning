/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
import entities.Seccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface HorariosLocal {
    //public Horario findBybloqueCarreraPlanNivelAnioYSemestre(String bloque, int codigo, String plan, int nivel, int anio, int semestre);
    
    public Horario findByBloqueAndProfesor(String bloque, String rutProfesor);
    
    public Horario findDisponibleByBloqueAndProfesor(String bloque, String rutProfesor);
    
    public List<Horario> findDisponiblesByProfesorId(String rutProfesor);
    
    public List<Horario> findByHorariosNoDisponibles();
    
    public List<Horario> findBySeleccionados(String rutProfesor);
    
    public List<Horario> findAsignadosByProfesorId(String rutProfesor);
    
    public Horario findBybloqueCarreraPlanNivelAnioYSemestre(String bloque, long idPlan, int nivel, int anio, int semestre);
    
    public List<Horario> AsignadoByBloqueAndProfesor(String bloque, String rutProfesor);
    
    public List<Horario> findAsignadosActualesByProfesorId(String rutProfesor, int anio, int semestre);
    
    public List<Horario> findByVersionPlanAndSemestreAndAnioAndNivel(Long idPlan, int semestre, int anio, int nivel);
}
