/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.VersionPlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface VersionPlanFacadeLocal {

    void create(VersionPlan versionPlan);

    void edit(VersionPlan versionPlan);

    void remove(VersionPlan versionPlan);

    VersionPlan find(Object id);

    List<VersionPlan> findAll();

    List<VersionPlan> findRange(int[] range);

    int count();
    
}
