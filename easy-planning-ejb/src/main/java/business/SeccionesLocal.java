/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Seccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface SeccionesLocal {
    //public List<Seccion> findBySemestreAnioCarreraPlan(int codCarrera, String plan, int anio, int semestre);
    
    public List<Seccion> findByAsignaturaAnioYSemestre(long asg, int anio, int semestre);
}
