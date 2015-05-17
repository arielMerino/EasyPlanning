/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface HorariosLocal {
    public Horario findBybloqueCarreraPlanNivelAnioYSemestre(String bloque, int codigo, String plan, int nivel, int anio, int semestre);
    
    public Horario findByBloqueAndProfesor(String bloque, Long idProfesor);
    
    public Horario findDisponibleByBloqueAndProfesor(String bloque, long idProfesor);
    
    public List<Horario> findDisponiblesByProfesorId(long profesorId);
}
