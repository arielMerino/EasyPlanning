/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface CoordinacionesLocal {
    public Coordinacion findByAsignaturaAndAñoAndSemestre(Asignatura asignatura, int año, int semestre);
    
    public List<Coordinacion> findByCarreraAndPlanAndAñoAndSemestre(int carrera, String plan, int año, int semestre);
}