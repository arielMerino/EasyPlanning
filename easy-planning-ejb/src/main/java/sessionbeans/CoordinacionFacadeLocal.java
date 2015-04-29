/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Coordinacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface CoordinacionFacadeLocal {

    void create(Coordinacion coordinacion);

    void edit(Coordinacion coordinacion);

    void remove(Coordinacion coordinacion);

    Coordinacion find(Object id);

    List<Coordinacion> findAll();

    List<Coordinacion> findRange(int[] range);

    int count();
    
}
