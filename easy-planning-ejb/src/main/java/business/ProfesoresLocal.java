/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Encuesta;
import entities.Profesor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface ProfesoresLocal {
    public Profesor findByRut(String rut);
        
    public List<Profesor> findDisponiblesByBloque(String bloque);
    
    public Encuesta getEncuestaBySemestreAndAnio(String rutProfesor, int semestre, int anio);
    
    public Profesor getProfesorByHorarioAsignado(Long id_asignatura, int anio, int semestre);
    
    public List<Profesor> getProfesoresByHorarioAsignado(Long id_asignatura, int anio, int semestre);
    
    //public Profesor getProfesorByRut(String rut);
}
