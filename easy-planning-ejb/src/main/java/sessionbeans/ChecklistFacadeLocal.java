/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Checklist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface ChecklistFacadeLocal {

    void create(Checklist checklist);

    void edit(Checklist checklist);

    void remove(Checklist checklist);

    Checklist find(Object id);

    List<Checklist> findAll();

    List<Checklist> findRange(int[] range);

    int count();
    
}
