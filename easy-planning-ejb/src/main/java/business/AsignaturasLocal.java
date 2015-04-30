/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface AsignaturasLocal {
    
    Asignatura findByCodigo(String codigo);
    
}
