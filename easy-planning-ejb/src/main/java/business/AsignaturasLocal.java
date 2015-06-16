/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface AsignaturasLocal {
    
    //public Asignatura findByCodigoAndPlan(String codigo, String plan);

    public List<Asignatura> getAllProfesorAsignatura();
    
    public List<Asignatura> findByCarreraAndPlan( long version);
    
    //public Asignatura findByCarreraAndCodigoAndPlan(int carrera, String codigo, String plan);
    
    public Asignatura findByCodigoAsgAndIdVersion(String codigo, long idVersion);
    
    public List<Integer> findNivelesByPlan(long idVersionPlan);
    
    public List<Asignatura> findByNivelAndPlan(int nivel, long versionPlan);
    
    //public List<String> findPlanesByCodigoCarrera(int codigo);
    
    //public List<Integer> findNivelesByCodigoCarreraAndPlan(int codigoCarrera, String plan);
    
    public List<Asignatura> findByAsignaturaAsignada(String rutProfesor, int anio, int semestre);
}
