/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.PlanEstudio;
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
public class PlanesEstudio implements PlanesEstudioLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public List<PlanEstudio> findPlanByIdCarrera(long idCarrera){
        Query query = em.createNamedQuery("PlanEstudio.findByCarreraId").setParameter("idCarrera", idCarrera);
        try{
            return (List<PlanEstudio>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
}
