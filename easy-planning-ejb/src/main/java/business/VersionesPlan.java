/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.VersionPlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ariel-linux
 */
@Stateless
public class VersionesPlan implements VersionesPlanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public List<VersionPlan> findByIdPlan(long idPlan){
        Query query = em.createNamedQuery("VersionPlan.findByIdPlan").setParameter("idPlan", idPlan);
        try{
            return (List<VersionPlan>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<VersionPlan> findByPlanificado(boolean planificado) {
        Query query = em.createNamedQuery("VersionPlan.findByPlanificado").setParameter("planificado", planificado);
        try{
            return (List<VersionPlan>) query.getResultList();
        }
        catch(NoResultException e){
            return new ArrayList();
        }
    }
}
