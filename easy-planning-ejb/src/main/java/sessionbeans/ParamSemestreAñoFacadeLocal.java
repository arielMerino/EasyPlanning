/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ParamSemestreAño;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jano
 */
@Local
public interface ParamSemestreAñoFacadeLocal {

    void create(ParamSemestreAño paramSemestreAño);

    void edit(ParamSemestreAño paramSemestreAño);

    void remove(ParamSemestreAño paramSemestreAño);

    ParamSemestreAño find(Object id);

    List<ParamSemestreAño> findAll();

    List<ParamSemestreAño> findRange(int[] range);

    int count();
    
}
