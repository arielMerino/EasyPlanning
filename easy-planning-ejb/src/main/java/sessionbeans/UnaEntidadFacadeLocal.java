/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.UnaEntidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alonso
 */
@Local
public interface UnaEntidadFacadeLocal {

    void create(UnaEntidad unaEntidad);

    void edit(UnaEntidad unaEntidad);

    void remove(UnaEntidad unaEntidad);

    UnaEntidad find(Object id);

    List<UnaEntidad> findAll();

    List<UnaEntidad> findRange(int[] range);

    int count();
    
}
