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
    
    public Asignatura findByCodigoAndPlan(String codigo, String plan);
<<<<<<< HEAD
    public List<Asignatura> getAllProfesorAsignatura();
=======
>>>>>>> a85907af41db33985c452e485f431bafb93c7bfa
    
    public List<Asignatura> findByCarreraAndPlan(String carrera, String plan);
    
    public List<Asignatura> findByNivelAndCarreraAndPlan(int nivel, String carrera, String plan);
    
    public Asignatura findByCarreraAndCodigoAndPlan(int carrera, String codigo, String plan);
}
