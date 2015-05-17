/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ParamSemestreAno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface ParamSemestreAnioFacadeLocal {

    void create(ParamSemestreAno paramSemestreAnio);

    void edit(ParamSemestreAno paramSemestreAnio);

    void remove(ParamSemestreAno paramSemestreAnio);

    ParamSemestreAno find(Object id);

    List<ParamSemestreAno> findAll();

    List<ParamSemestreAno> findRange(int[] range);

    int count();
    
    ParamSemestreAno findById(Long id);
    
}
