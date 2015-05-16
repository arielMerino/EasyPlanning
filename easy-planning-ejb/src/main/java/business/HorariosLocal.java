/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface HorariosLocal {
    public Horario findBybloqueCarreraPlanNivelAñoYSemestre(String bloque, int codigo, String plan, int nivel, int año, int semestre);
    
    public Horario findByBloqueAndProfesor(String bloque, Long idProfesor);
}
