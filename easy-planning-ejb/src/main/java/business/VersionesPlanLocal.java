/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.VersionPlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface VersionesPlanLocal {
    public List<VersionPlan> findByIdPlan(long idPlan);
    public List<VersionPlan> findByPlanificado(boolean planificado);
}
