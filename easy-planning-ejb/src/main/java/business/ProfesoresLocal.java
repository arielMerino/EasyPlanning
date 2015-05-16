/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Profesor;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface ProfesoresLocal {
    public Profesor findByRut(String rut);
}
