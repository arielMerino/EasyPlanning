/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.PlanEstudio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface PlanEstudioFacadeLocal {

    void create(PlanEstudio planEstudio);

    void edit(PlanEstudio planEstudio);

    void remove(PlanEstudio planEstudio);

    PlanEstudio find(Object id);

    List<PlanEstudio> findAll();

    List<PlanEstudio> findRange(int[] range);

    int count();
    
}
