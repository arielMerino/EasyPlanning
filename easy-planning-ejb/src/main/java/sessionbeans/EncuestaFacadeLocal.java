/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Encuesta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface EncuestaFacadeLocal {

    void create(Encuesta encuesta);

    void edit(Encuesta encuesta);

    void remove(Encuesta encuesta);

    Encuesta find(Object id);

    List<Encuesta> findAll();

    List<Encuesta> findRange(int[] range);

    int count();
    
}
